package com.project.libgen.presentation.book_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.libgen.core.util.Resource
import com.project.libgen.presentation.components.util.Mode
import com.project.libgen.presentation.components.util.UserState
import com.project.libgen.use_case.get_book_list.GetBookListUseCase
import com.project.libgen.use_case.get_book_list.GetFictionBookListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase,
    private val getFictionBookListUseCase: GetFictionBookListUseCase
) : ViewModel() {

    private var _currentUser: MutableLiveData<UserState> =
        MutableLiveData(UserState(user = Firebase.auth.currentUser))
    val currentUser: LiveData<UserState>
        get() = _currentUser

    private var _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private var _bookList = mutableStateOf(BookListState())
    val bookList: State<BookListState>
        get() = _bookList

    val filterText =
        listOf("Title", "Author(s)", "Series", "Publisher", "Year", "Language", "Tags", "Extension")
    private val filterOptions =
        listOf("title", "author", "series", "publisher", "year", "language", "tags", "extension")
    val filterIndex = mutableStateOf(0)

    fun onSearchQueryChanged(newSearchQuery: String) {
        _searchQuery.value = newSearchQuery
    }

    val searched = mutableStateOf(false)

    var modeState = MutableLiveData(Mode.NONFICTION)

    init {
        _searchQuery.value = "algorithm"
    }

    fun toggleSection() {
        when (modeState.value) {
            Mode.FICTION -> {
                modeState.postValue(Mode.NONFICTION)
                searched.value = false
                _bookList.value = BookListState(bookList = emptyList())
            }
            Mode.NONFICTION -> {
                modeState.postValue(Mode.FICTION)
                searched.value = false
                _bookList.value = BookListState(bookList = emptyList())
            }
            null -> {}
        }
    }

    fun onSearch() {
        when (modeState.value) {
            Mode.NONFICTION -> {
                onNonFictionSearch()
            }
            Mode.FICTION -> {
                onFictionSearch()
            }
            null -> {}
        }
    }

    private fun onNonFictionSearch() {
        getBookListUseCase(
            _searchQuery.value,
            filterOptions[filterIndex.value]
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    searched.value = true
                    _bookList.value = BookListState(bookList = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _bookList.value = BookListState(
                        error = result.message ?: "Something went wrong."
                    )
                }
                is Resource.Loading -> {
                    _bookList.value = BookListState(isLoading = true)
                }
            }
        }
            .launchIn(CoroutineScope(IO)) // NOTE: Don't use .launchIn(viewModelScope), since this function is network related.
    }

    private fun onFictionSearch() {
        getFictionBookListUseCase(_searchQuery.value)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        searched.value = true
                        _bookList.value = BookListState(bookList = result.data ?: emptyList())
                    }
                    is Resource.Error -> {
                        _bookList.value = BookListState(
                            error = result.message ?: "Something went wrong."
                        )
                    }
                    is Resource.Loading -> {
                        _bookList.value = BookListState(isLoading = true)
                    }
                }
            }
            .launchIn(CoroutineScope(IO)) // NOTE: Don't use .launchIn(viewModelScope), since this function is network related.
    }

    fun logout() {
        _currentUser.postValue(UserState(user = null))
        Firebase.auth.signOut()
    }
}