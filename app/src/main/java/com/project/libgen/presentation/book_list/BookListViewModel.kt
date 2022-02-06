package com.project.libgen.presentation.book_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.project.libgen.data.model.Book
import com.project.libgen.repository.LibGenSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val LibGenSearch: LibGenSearchRepository
) : ViewModel() {
    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

//    private var bookList = MutableLiveData(listOf<Book>())
//    private var _bookList: MutableLiveData<MutableList<Book>> = MutableLiveData(mutableListOf())
//    val bookList: LiveData<List<Book>> = _bookList

    private val _bookList = mutableStateOf(listOf<Book>())
    val bookList = _bookList

    private val _filterVisible = mutableStateOf(false)
    val filterVisible = _filterVisible

//    val filterOptions = listOf<String>("Title", "Author(s)", "Series", "Publisher", "Year", "ISBN", "Language", "MD5", "Tags", "Extension")

    val filterText = listOf("Title", "Author(s)", "Series", "Publisher", "Year", "Language", "Tags", "Extension")
    val filterOptions = listOf("title", "author", "series", "publisher", "year", "language", "tags", "extension")
    val filterIndex = mutableStateOf(0)

    init {
        _searchQuery.value = "algorithm"
    }


    fun onSearchQueryChanged(newSearchQuery: String) {
        _searchQuery.value = newSearchQuery
    }

    fun onSearch(searchQuery: String, filterOption: String) {
        _bookList.value = LibGenSearch.getBooks(searchQuery, filterOption)
    }

}