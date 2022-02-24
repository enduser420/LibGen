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
import java.io.File
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val application: Application,
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
//        getBookmarkBool()
        savedStateHandle.get<String>("id")?.let { bookId ->
            getBookDetails(bookId)
        }
        savedStateHandle.get<String>("downloadlink")?.let { bookLink ->
            downloadlink.value = bookLink
        }
    }

    private fun getBookDetails(bookId: String) {
        getBookDetailsUseCase(bookId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    // apply the received download from the list_screen to the book object
                    _bookState.value = BookDetailsState(book = result.data.apply {
                        result.data?.downloadlink = downloadlink.value
                        result.data?.bookmarked = bookmarked.value ?: true
                    })
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
            bookmarked.value = bookmarkUseCases.getBookmarkBool(_bookState.value.book?.id ?: "")
        }
    }

    private fun downloadFile() {
        val fileName = "${_bookState.value.book?.title}.${_bookState.value.book?.extension}"
        val file =
            File(Environment.DIRECTORY_DOCUMENTS + fileName)
        if (file.exists()) {
            return
        } else {
            CoroutineScope(IO).launch {
                val getLink = LibGenBookDownload.downloadBookLink(downloadlink.value)
                val request = DownloadManager.Request(Uri.parse(getLink))
                request.setTitle("LibGen: ${_bookState.value.book?.title}")
                request.setDescription("Downloading ${_bookState.value.book?.title}")
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
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
                    bookmarkUseCases.deleteBookmark(_bookState.value.book.apply {
                        _bookState.value.book?.bookmarked = false
                    })
                    bookmarked.value = false
                }
            }
            is BookDetailsEvent.downloadBook -> {
                downloadFile()
            }
        }
    }
}