package com.project.libgen.presentation.bookmark_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.libgen.presentation.book_details.BookDetailsState
import com.project.libgen.repository.LibGenDownloadRepository
import com.project.libgen.use_case.bookmark.BookmarkUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkDetailsViewModel @Inject constructor(
    private val bookmarkUseCases: BookmarkUseCases,
    private val LibGenBookDownload: LibGenDownloadRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _bookState = mutableStateOf(BookDetailsState())
    val bookState: State<BookDetailsState>
        get() = _bookState
    private var currentBookmarkId: String? = null
    private val downloadlink = mutableStateOf("")

    init {
        savedStateHandle.get<String>("id")?.let { bookmarkId ->
            viewModelScope.launch {
                bookmarkUseCases.getBookmark(bookmarkId)?.also { bookmark ->
                    currentBookmarkId = bookmark.id
                    val currentBookmark = bookmarkUseCases.getBookmark(bookmarkId)
                    currentBookmark?.downloadlink?.let {
                        downloadlink.value = it
                    }
                }
            }
        }
    }

    fun onEvent(event: BookmarkDetailsEvent) {
        when (event) {
            is BookmarkDetailsEvent.starBook -> {
                viewModelScope.launch {
//                    bookmarkUseCases.insertLocalBookmark(_bookState.value.book.apply { _bookState.value.book?.bookmarked = true })
                }
            }
            is BookmarkDetailsEvent.unstarBook -> {
                viewModelScope.launch {
//                    bookmarkUseCases.deleteLocalBookmark(_bookState.value.book.apply { _bookState.value.book?.bookmarked = false })
                }
            }
            is BookmarkDetailsEvent.downloadBook -> {
//                CoroutineScope(IO).launch {
//                    Log.d("DownloadLink", LibGenBookDownload.downloadBookLink(downloadlink.value))
//                }
            }
        }
    }
}