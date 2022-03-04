package com.project.libgen.presentation.book_details

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.libgen.core.util.Resource
import com.project.libgen.data.data_source.BookDao
import com.project.libgen.presentation.components.util.UserState
import com.project.libgen.repository.LibGenDownloadRepository
import com.project.libgen.use_case.bookmark.BookmarkUseCases
import com.project.libgen.use_case.get_book_details.GetBookDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    application: Application,
    private val getBookDetailsUseCase: GetBookDetailsUseCase,
    private val bookmarkUseCases: BookmarkUseCases,
    private val LibGenBookDownload: LibGenDownloadRepository,
    private val bookDao: BookDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _bookState = mutableStateOf(BookDetailsState())
    val bookState: State<BookDetailsState>
        get() = _bookState
    private val downloadlink = mutableStateOf("")
    val bookmarked = mutableStateOf(_bookState.value.book?.bookmarked)
    private val downloadManager =
        application.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    private var _currentUser: MutableLiveData<UserState> =
        MutableLiveData(UserState(user = Firebase.auth.currentUser))
    val currentUser: LiveData<UserState>
        get() = _currentUser

    init {
        savedStateHandle.get<String>("id")?.let { bookId ->
            getBookDetails(bookId)
        }
        savedStateHandle.get<String>("downloadlink")?.let { bookLink ->
            val decoded = URLDecoder.decode(bookLink, StandardCharsets.UTF_8.toString())
            downloadlink.value = decoded
        }
    }

    private fun getBookDetails(bookId: String) {
        getBookDetailsUseCase(bookId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { book ->
                        _bookState.value = BookDetailsState(book = book.apply {
                            book.downloadlink = downloadlink
                            book.bookmarked = bookmarked
                        })
                        bookmarked.value = bookmarkUseCases.getBookmarkBool(book.id)
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
        }.launchIn(viewModelScope)
    }

    private fun downloadFile() {
        _bookState.value.book?.let { book ->
            val fileName = "${book.title}.${book.extension}"
            val getLink = LibGenBookDownload.downloadBookLink(downloadlink.value)
            getLink?.let { link ->
                val request = DownloadManager.Request(Uri.parse(link))
                request.setTitle("LibGen: ${book.title}")
                request.setDescription("Downloading ${book.title}")
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                request.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOCUMENTS,
                    fileName
                )
                downloadManager.enqueue(request)
            }
        }
    }

    fun onEvent(event: BookDetailsEvent) {
        when (event) {
            is BookDetailsEvent.starBook -> {
                viewModelScope.launch {
                    _bookState.value.book?.let {
                        bookmarkUseCases.insertBookmark(it.apply {
                            it.bookmarked = true
                        })
                        Firebase.auth.currentUser?.let { user ->
                            bookDao.addBook(it.apply {
                                it.bookmarked = true
                                it.userId = user.uid
                            })
                        }
                    }
                }
                bookmarked.value = true
            }
            is BookDetailsEvent.unstarBook -> {
                viewModelScope.launch {
                    _bookState.value.book?.let {
                        bookmarkUseCases.deleteBookmark(it.apply {
                            it.bookmarked = false
                        })
                        Firebase.auth.currentUser?.let { user ->
                            bookDao.deleteBook(
                                userId = user.uid,
                                bookId = it.id
                            )
                        }
                    }
                }
                bookmarked.value = false
            }
            is BookDetailsEvent.downloadBook -> {
                downloadFile()
            }
        }
    }
}