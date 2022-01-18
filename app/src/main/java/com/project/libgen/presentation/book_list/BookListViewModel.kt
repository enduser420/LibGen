package com.project.libgen.presentation.book_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.project.libgen.data.model.Book
import com.project.libgen.data.remote.LibGenSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val LibGenSearch: LibGenSearch
) : ViewModel() {
    private val _searchQuery: MutableState<String> = mutableStateOf("")
    val searchQuery: MutableState<String> = _searchQuery

//    private var bookList = MutableLiveData(listOf<Book>())
//    private var _bookList: MutableLiveData<MutableList<Book>> = MutableLiveData(mutableListOf())
//    val bookList: LiveData<List<Book>> = _bookList

    private val _bookList: MutableState<List<Book>> = mutableStateOf(listOf())
    val bookList: MutableState<List<Book>> = _bookList

    init {

        searchQuery.value = "algorithm"
        onSearch(searchQuery.value)

    }

    fun onSearchQueryChanged(newSearchQuery: String) {
        searchQuery.value = newSearchQuery
    }

    fun onSearch(searchQuery: String) {
        bookList.value = LibGenSearch.getBooks(searchQuery)
    }
}