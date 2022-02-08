package com.project.libgen.presentation.book_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.libgen.core.util.Resource
import com.project.libgen.data.model.Book
import com.project.libgen.repository.LibGenSearchRepository
import com.project.libgen.use_case.get_book_list.GetBookListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val LibGenSearch: LibGenSearchRepository,
    private val getBookListUseCase: GetBookListUseCase
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

//    private var bookList = MutableLiveData(listOf<Book>())
//    private var _bookList: MutableLiveData<MutableList<Book>> = MutableLiveData(mutableListOf())
//    val bookList: LiveData<List<Book>> = _bookList

//    private val _bookList = mutableStateOf(BookListState())
//    val bookList: State<BookListState> = _bookList
    private val _bookList = mutableStateOf(listOf<Book>())
    val bookList = _bookList

    private val _filterVisible = mutableStateOf(false)
    val filterVisible = _filterVisible

//    val filterOptions = listOf<String>("Title", "Author(s)", "Series", "Publisher", "Year", "ISBN", "Language", "MD5", "Tags", "Extension")

    val filterText = listOf("Title", "Author(s)", "Series", "Publisher", "Year", "Language", "Tags", "Extension")
    private val filterOptions = listOf("title", "author", "series", "publisher", "year", "language", "tags", "extension")
    val filterIndex = mutableStateOf(0)

    init {
        _searchQuery.value = "algorithm"
    }

    fun onSearchQueryChanged(newSearchQuery: String) {
        _searchQuery.value = newSearchQuery
    }

//    fun getBookList(searchQuery: String = _searchQuery.value, filterOption: String = filterOptions[filterIndex.value]) {
//        getBookListUseCase(searchQuery, filterOption).onEach { result ->
//            when (result) {
//                is Resource.Success -> {
//                    println("success")
////                    _bookList.value = bookList.value.copy(bookList = result.data ?: listOf(Book(), Book()))
//                    _bookList.value = BookListState(bookList = result.data ?: emptyList())
//                }
//                is Resource.Error -> {
//                    _bookList.value = BookListState(
//                        error = result.message ?: "An unexpected error occurred"
//                    )
//                }
//                is Resource.Loading -> {
//                    _bookList.value = BookListState(isLoading = true)
//                }
//            }
//        }.launchIn(viewModelScope)
//    }

    fun onSearch(searchQuery: String = _searchQuery.value, filterOption: String = filterOptions[filterIndex.value]) {
        _bookList.value = LibGenSearch.getBooks(searchQuery, filterOption)
    }
}