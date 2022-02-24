package com.project.libgen.presentation.book_details

import com.project.libgen.Screen
import com.project.libgen.data.model.Book

data class BookDetailsState(
    val isLoading: Boolean = false,
    val book: Book? = null,
    val error: String = ""
)