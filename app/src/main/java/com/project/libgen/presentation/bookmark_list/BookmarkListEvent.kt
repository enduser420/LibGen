package com.project.libgen.presentation.bookmark_list

sealed class BookmarkListEvent {
    object deleteAllBookmark: BookmarkListEvent()
}
