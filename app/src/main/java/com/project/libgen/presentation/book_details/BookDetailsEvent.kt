package com.project.libgen.presentation.book_details

import com.project.libgen.data.model.Book
import com.project.libgen.presentation.bookmark_details.BookmarkDetailsEvent

sealed class BookDetailsEvent {
    object starBook: BookDetailsEvent()
    object unstarBook: BookDetailsEvent()
    object downloadBook: BookDetailsEvent()
    object getTorrent: BookDetailsEvent()
}
