package com.project.libgen.presentation.book_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.project.libgen.core.util.Resource
import com.project.libgen.use_case.get_book_details.GetBookDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val getBookDetailsUseCase: GetBookDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _bookState = mutableStateOf(BookDetailsState())
    val bookState: State<BookDetailsState> = _bookState

    init {
        savedStateHandle.get<String>("id")?.let { bookId ->
            getBookDetails(bookId)
        }
    }

    private fun getBookDetails(bookId: String) {
        getBookDetailsUseCase(bookId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _bookState.value = BookDetailsState(book = result.data)
                }
                is Resource.Error -> {
                    _bookState.value = BookDetailsState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _bookState.value = BookDetailsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}