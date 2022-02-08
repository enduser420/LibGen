package com.project.libgen.presentation.book_list

import com.project.libgen.data.model.Book

data class BookListState(
    val isLoading: Boolean = false,
    val bookList: List<Book> = emptyList(),
    val error: String = ""
)