package com.project.libgen.presentation.book_details

import com.project.libgen.data.model.Book

sealed class BookDetailsEvent {
    object starBook: BookDetailsEvent()
    object unstarBook: BookDetailsEvent()
    object downloadBook: BookDetailsEvent()
}
