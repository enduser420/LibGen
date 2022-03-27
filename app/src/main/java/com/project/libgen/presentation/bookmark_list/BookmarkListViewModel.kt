package com.project.libgen.presentation.bookmark_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.libgen.core.util.Resource
import com.project.libgen.use_case.bookmark.BookmarkUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkListViewModel @Inject constructor(
    private val bookmarkUseCases: BookmarkUseCases
) : ViewModel() {

    private val _bookmarkList = mutableStateOf(BookmarkListState())
    val bookmarkList: State<BookmarkListState>
        get() = _bookmarkList
    private var getBookmarksJob: Job? = null

//    init {
//        getLocalBookmarks() // Look in BookmarkListScreen line 42: for the reason this is not used
//    }

    fun getBookmarks() {
        getBookmarksJob?.cancel()
        getBookmarksJob = bookmarkUseCases.getLocalBookmarks().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _bookmarkList.value =
                        BookmarkListState(
                            bookmarkList = result.data ?: emptyList()
                        )
                }
                is Resource.Error -> {
                    _bookmarkList.value =
                        BookmarkListState(error = result.message ?: "Something went wrong.")
                }
                is Resource.Loading -> {
                    _bookmarkList.value = BookmarkListState(isLoading = true)
                }
            }
        }.launchIn(CoroutineScope(IO))
    }

    fun onEvent(event: BookmarkListEvent) {
        when (event) {
            is BookmarkListEvent.deleteAllBookmark -> {
                viewModelScope.launch {
                    bookmarkUseCases.deleteAllLocalBookmarks()
                }.invokeOnCompletion {
                    _bookmarkList.value = BookmarkListState()
                }
            }
        }
    }
}
