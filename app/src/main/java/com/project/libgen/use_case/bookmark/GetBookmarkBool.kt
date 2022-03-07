package com.project.libgen.use_case.bookmark

import com.project.libgen.repository.BookmarkRepository

class GetBookmarkBool(
    private val repository: BookmarkRepository
) {
    suspend operator fun invoke(userId: String, bookId: String): Boolean {
        return repository.getBookmarkBool(userId, bookId)
    }
}