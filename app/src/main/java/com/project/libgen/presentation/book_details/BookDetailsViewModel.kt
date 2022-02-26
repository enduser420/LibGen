package com.project.libgen.presentation.book_details

import android.annotation.SuppressLint
import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.libgen.core.util.Resource
import com.project.libgen.repository.LibGenDownloadRepository
import com.project.libgen.use_case.bookmark.BookmarkUseCases
import com.project.libgen.use_case.get_book_details.GetBookDetailsUseCase
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
class BookDetailsViewModel @Inject constructor(
    application: Application,
    private val getBookDetailsUseCase: GetBookDetailsUseCase,
    private val bookmarkUseCases: BookmarkUseCases,
    private val LibGenBookDownload: LibGenDownloadRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _bookState = mutableStateOf(BookDetailsState())
    val bookState: State<BookDetailsState> = _bookState
    private val downloadlink = mutableStateOf("")
    val bookmarked = mutableStateOf(_bookState.value.book?.bookmarked)
    private val downloadManager =
        application.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    init {
        savedStateHandle.get<String>("id")?.let { bookId ->
            getBookDetails(bookId)
        }
        getBookmarkBool()

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

    private fun getBookmarkBool() {
        viewModelScope.launch {
            _bookState.value.book?.let { book ->
                bookmarked.value = bookmarkUseCases.getBookmarkBool(book.id)
            }
        }
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

    @SuppressLint("RestrictedApi")
    fun onEvent(event: BookDetailsEvent) {
        when (event) {
            is BookDetailsEvent.starBook -> {
                CoroutineScope(IO).launch {
                    bookmarkUseCases.insertBookmark(_bookState.value.book.apply {
                        _bookState.value.book?.bookmarked = true
                    })
                }
                bookmarked.value = true
            }
            is BookDetailsEvent.unstarBook -> {
                viewModelScope.launch {
                    _bookState.value.book?.let { book ->
                        bookmarkUseCases.deleteBookmark(book.apply {
                            book.bookmarked = false
                        })
                    }
                    bookmarked.value = false
                }
            }
            is BookDetailsEvent.downloadBook -> {
                downloadFile()
            }
        }
    }
}