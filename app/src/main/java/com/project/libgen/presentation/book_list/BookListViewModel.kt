package com.project.libgen.presentation.book_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.project.libgen.data.model.Book
import com.project.libgen.data.remote.LibGenSearch
import com.project.libgen.repository.LibGenSearchImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val LibGenSearch: LibGenSearch
) : ViewModel() {
    private val _searchQuery: MutableState<String> = mutableStateOf("")
    val searchQuery: MutableState<String> = _searchQuery

//    private var _booklist = MutableLiveData(listOf<Book>())
//    private var _booklist: MutableLiveData<MutableList<Book>> = MutableLiveData(mutableListOf())
//    val booklist: LiveData<List<Book>> = _booklist

    private val _bookList: MutableState<List<Book>> = mutableStateOf(listOf())
    val bookList: MutableState<List<Book>> = _bookList

    init {

        searchQuery.value = "algorithm"
//        onSearch(searchQuery.value)

    }

    fun onSearchQueryChanged(newSearchQuery: String) {
        _searchQuery.value = newSearchQuery
    }

    fun onSearch(searchQuery: String) {
        bookList.value = LibGenSearch.getBooks(searchQuery)
    }
}