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
    private val _searchQuery: MutableState<String> = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

//    private var bookList = MutableLiveData(listOf<Book>())
//    private var _bookList: MutableLiveData<MutableList<Book>> = MutableLiveData(mutableListOf())
//    val bookList: LiveData<List<Book>> = _bookList

    private val _bookList: MutableState<List<Book>> = mutableStateOf(listOf())
    val bookList: State<List<Book>> = _bookList

    init {

        _searchQuery.value = "algorithm"
//        onSearch(_searchQuery.value)
    }


    fun onSearchQueryChanged(newSearchQuery: String) {
        _searchQuery.value = newSearchQuery
    }

    fun onSearch(searchQuery: String) {
        _bookList.value = LibGenSearch.getBooks(searchQuery)
    }

}