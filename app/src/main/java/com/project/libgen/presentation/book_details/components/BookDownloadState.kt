package com.project.libgen.presentation.book_details.components

data class BookDownloadState(
    val isLoading: Boolean = false,
    val error: String = ""
)