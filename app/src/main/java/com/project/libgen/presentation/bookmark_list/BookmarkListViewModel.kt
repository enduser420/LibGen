package com.project.libgen.presentation.bookmark_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.libgen.core.util.Resource
import com.project.libgen.presentation.components.util.UserState
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

    private var _currentUser: MutableLiveData<UserState> =
        MutableLiveData(UserState(user = Firebase.auth.currentUser))
    val currentUser: LiveData<UserState>
        get() = _currentUser
    private val _bookmarkList = mutableStateOf(BookmarkListState())
    val bookmarkList: State<BookmarkListState>
        get() = _bookmarkList
    private var getBookmarksJob: Job? = null

//    init {
//        getLocalBookmarks() // Look in BookmarkListScreen line 42: for the reason this is not used
//    }

    fun getBookmarks() {
        getBookmarksJob?.cancel()
        _currentUser.value?.user?.let {
            if (it.isAnonymous) {
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
            } else {
                getBookmarksJob = bookmarkUseCases.getBookmarks(it.uid).onEach { result ->
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
        }
    }

//    fun getLocalBookmarks(userId: String) {
//        getBookmarksJob?.cancel()
//        getBookmarksJob = bookmarkUseCases.getLocalBookmarks(userId).onEach { result ->
//            when (result) {
//                is Resource.Success -> {
//                    _bookmarkList.value =
//                        BookmarkListState(
//                            bookmarkList = result.data ?: emptyList()
//                        )
//                }
//                is Resource.Error -> {
//                    _bookmarkList.value =
//                        BookmarkListState(error = result.message ?: "Something went wrong.")
//                }
//                is Resource.Loading -> {
//                    _bookmarkList.value = BookmarkListState(isLoading = true)
//                }
//            }
//        }.launchIn(CoroutineScope(IO))
//    }

    fun onEvent(event: BookmarkListEvent) {
        when (event) {
            is BookmarkListEvent.deleteAllBookmark -> {
                viewModelScope.launch {
                    _currentUser.value?.user?.let {
                        if (it.isAnonymous) {
                            bookmarkUseCases.deleteAllLocalBookmarks()
                        } else {
                            bookmarkUseCases.deleteAllBookmarks(it.uid)
                        }
                    }
                }.invokeOnCompletion {
                    _bookmarkList.value = BookmarkListState()
                }
            }
        }
    }
}