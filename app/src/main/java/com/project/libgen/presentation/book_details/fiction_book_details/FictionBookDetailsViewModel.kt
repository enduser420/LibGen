package com.project.libgen.presentation.book_details.fiction_book_details

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.libgen.core.util.Resource
import com.project.libgen.data.data_source.BookmarkDao
import com.project.libgen.presentation.book_details.BookDetailsEvent
import com.project.libgen.presentation.book_details.BookDetailsState
import com.project.libgen.presentation.book_details.components.BookDownloadState
import com.project.libgen.presentation.components.util.Mode
import com.project.libgen.presentation.components.util.UserState
import com.project.libgen.use_case.bookmark.BookmarkUseCases
import com.project.libgen.use_case.get_book_details.DownloadBookUseCase
import com.project.libgen.use_case.get_book_details.GetFictionBookDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class FictionBookDetailsViewModel @Inject constructor(
    application: Application,
    private val getFictionBookDetailsUseCase: GetFictionBookDetailsUseCase,
    private val bookmarkUseCases: BookmarkUseCases,
    private val LibGenBookDownloadUseCase: DownloadBookUseCase,
    private val bookmarkDao: BookmarkDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _bookState = mutableStateOf(BookDetailsState())
    val bookState: State<BookDetailsState>
        get() = _bookState

    private val _downloadState = mutableStateOf(BookDownloadState())
    val downloadState
        get() = _downloadState

    private val _downloadlink = mutableStateOf("")
    val downloadlink
        get() = _downloadlink

    val bookmarked = mutableStateOf(_bookState.value.book?.bookmarked)

    private var _currentUser: MutableLiveData<UserState> =
        MutableLiveData(UserState(user = Firebase.auth.currentUser))

    private val _md5 = mutableStateOf("")

    private val downloadManager =
        application.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    init {
        savedStateHandle.get<String>("md5")?.let { md5 ->
            _md5.value = md5
        }

        savedStateHandle.get<String>("downloadlink")?.let { bookLink ->
            val decoded = URLDecoder.decode(bookLink, StandardCharsets.UTF_8.toString())
            _downloadlink.value = decoded
        }
    }

    fun getBookDetails() {
        getFictionBookDetailsUseCase(_md5.value).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { book ->
                        _bookState.value = BookDetailsState(book = book)
                        _currentUser.value?.user?.let {
                            if (it.isAnonymous) {
                                bookmarked.value =
                                    bookmarkUseCases.getLocalBookmarkBool(book.id)
                            } else {
                                bookmarked.value =
                                    bookmarkUseCases.getBookmarkBool(it.uid, book.id)
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    _bookState.value = BookDetailsState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _bookState.value = BookDetailsState(isLoading = true)
                }
            }
        }.launchIn(CoroutineScope(IO))
    }

    private fun downloadFile() {
        _bookState.value.book?.let { book ->
            val fileName = "${book.title}.${book.extension}"

            LibGenBookDownloadUseCase(_downloadlink.value).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _downloadState.value = BookDownloadState(isLoading = true)
                    }
                    is Resource.Success -> {
                        result.data?.let {
                            val uri = Uri.parse(it[0])
                            val request = DownloadManager.Request(uri)
                            request.setTitle("LibGen: ${book.title}")
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            request.setDestinationInExternalPublicDir(
                                Environment.DIRECTORY_DOCUMENTS,
                                fileName
                            )
                            downloadManager.enqueue(request).run {
                                _downloadState.value = BookDownloadState()
                            }
                        }
                    }
                    is Resource.Error -> {
                        _downloadState.value = BookDownloadState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                }
            }.launchIn(CoroutineScope(IO))
        }
    }

    fun onEvent(event: BookDetailsEvent) {
        when (event) {
            is BookDetailsEvent.starBook -> {
                viewModelScope.launch {
                    _currentUser.value?.user?.let { user ->
                        _bookState.value.book?.let {
                            if (user.isAnonymous) {
                                bookmarkUseCases.insertBookmark(it.apply {
                                    it.mode = Mode.FICTION
                                    it.bookmarked = true
                                    it.downloadlink = _downloadlink.value
                                })
                            } else {
                                bookmarkDao.addBook(it.apply {
                                    it.mode = Mode.FICTION
                                    it.bookmarked = true
                                    it.userId = user.uid
                                    it.downloadlink = _downloadlink.value
                                })
                            }
                        }
                    }
                }
                bookmarked.value = true
            }
            is BookDetailsEvent.unstarBook -> {
                viewModelScope.launch {
                    Firebase.auth.currentUser?.let { user ->
                        _bookState.value.book?.let {
                            if (user.isAnonymous) {
                                bookmarkUseCases.deleteBookmark(it)
                            } else {
                                bookmarkDao.deleteBook(
                                    userId = user.uid,
                                    bookId = it.id
                                )
                            }
                        }
                    }
                }
                bookmarked.value = false
            }
            is BookDetailsEvent.downloadBook -> {
                downloadFile()
            }
            else -> {}
        }
    }
}