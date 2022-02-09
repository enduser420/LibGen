package com.project.libgen.presentation.book_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.project.libgen.core.util.Resource
import com.project.libgen.use_case.get_book_list.GetBookListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase
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

    init {
        _searchQuery.value = "algorithm"
    }

    fun onSearchQueryChanged(newSearchQuery: String) {
        _searchQuery.value = newSearchQuery
    }

    fun onSearch(
        searchQuery: String = _searchQuery.value,
        filterOption: String = filterOptions[filterIndex.value]
    ) {
        getBookListUseCase(searchQuery, filterOption).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    println("success")
//                    _bookList.value = bookList.value.copy(bookList = result.data ?: listOf(Book(), Book()))
                    _bookList.value = BookListState(bookList = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    println("error")
                    _bookList.value = BookListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    println("loading")
                    _bookList.value = BookListState(isLoading = true)
                }
            }
        }.launchIn(CoroutineScope(IO)) // Don't use .launchIn(viewModelScope)
    }
}