package com.project.libgen.presentation.bookmark_details

sealed class BookmarkDetailsEvent {
    data class starBook(val value: String): BookmarkDetailsEvent()
    data class unstarBook(val value: String): BookmarkDetailsEvent()
    object downloadBook: BookmarkDetailsEvent()
}
