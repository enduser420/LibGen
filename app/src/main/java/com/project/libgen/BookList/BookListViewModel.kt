package com.project.libgen.BookList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.libgen.data.model.Book
import com.project.libgen.data.remote.LibGenSearch
import com.project.libgen.repository.LibGenSearchImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookListViewModel : ViewModel() {

    private val LibGenSearch: LibGenSearch = LibGenSearchImpl()
    private val _searchQuery: MutableState<String> = mutableStateOf("")
    val searchQuery: MutableState<String> = _searchQuery

    private val _booklist: MutableState<List<Book>> = mutableStateOf(listOf())
    val booklist: MutableState<List<Book>> = _booklist

    init {

        searchQuery.value = "algorithm"
        viewModelScope.launch {
            onSearch("algorithm")
        }

    }

    fun onsearchQueryChange(newsearchQuery: String) {
        _searchQuery.value = newsearchQuery
    }

      fun onSearch(searchQuery: String) {
        booklist.value = LibGenSearch.getBooks(searchQuery)

    }
}