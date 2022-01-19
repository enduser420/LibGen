package com.project.libgen.presentation.book_details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.libgen.data.model.Book
import com.project.libgen.data.remote.toBook
import com.project.libgen.repository.LibGenBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val LibGenBookRepository: LibGenBookRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableState<Book> = mutableStateOf(Book())
    val state: State<Book> = _state

    init {
        savedStateHandle.get<String>("")?.let { bookId ->
            getBookDetails(bookId)
        }
    }

    private fun getBookDetails(bookId: String) {
        viewModelScope.launch {
            _state.value = LibGenBookRepository.getBookDetails(bookId).toBook()
        }
    }
}