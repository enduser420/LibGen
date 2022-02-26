package com.project.libgen.presentation.book_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.project.libgen.core.util.Resource
import com.project.libgen.repository.AuthRepository
import com.project.libgen.use_case.get_book_list.GetBookListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase,
    private val repository: AuthRepository
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _bookList = mutableStateOf(BookListState())
    val bookList: State<BookListState> = _bookList

    val filterText =
        listOf("Title", "Author(s)", "Series", "Publisher", "Year", "Language", "Tags", "Extension")
    private val filterOptions =
        listOf("title", "author", "series", "publisher", "year", "language", "tags", "extension")
    val filterIndex = mutableStateOf(0)

    fun onSearchQueryChanged(newSearchQuery: String) {
        _searchQuery.value = newSearchQuery
    }

    val searched = mutableStateOf(false)

    init {
        _searchQuery.value = "algorithm"
    }

    fun onSearch() {
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
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _bookList.value = BookListState(isLoading = true)
                }
            }
        }
            .launchIn(CoroutineScope(IO)) // NOTE: Don't use .launchIn(viewModelScope), since this function is not called from the init{} block but from the BookList Screen
    }

    fun logout() {
        repository.signOut()
    }
}