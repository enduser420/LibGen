package com.project.libgen.use_case.bookmark

import com.project.libgen.repository.LocalBookmarkRepository

data class DeleteAllLocalBookmarks(
    private val localRepository: LocalBookmarkRepository
) {
    suspend operator fun invoke() {
        return localRepository.deleteAllBookmarks()
    }
}