package com.project.libgen.use_case.bookmark

import com.project.libgen.repository.BookmarkRepository

class DeleteAllBookmarks(
    private val repository: BookmarkRepository
) {
    suspend operator fun invoke(userId: String) {
        repository.deleteAllBookmarks(userId)
    }
}