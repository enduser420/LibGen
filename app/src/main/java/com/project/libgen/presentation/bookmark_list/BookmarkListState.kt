package com.project.libgen.presentation.bookmark_list

import com.project.libgen.data.model.Book

data class BookmarkListState(
    val isLoading: Boolean = false,
    val bookmarkList: List<Book> = emptyList(),
    val error: String = ""
)