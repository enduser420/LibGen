package com.project.libgen.use_case.bookmark

import com.project.libgen.data.model.Book
import com.project.libgen.repository.LocalBookmarkRepository

class GetBookmark(
    private val repositoryLocal: LocalBookmarkRepository
) {
    suspend operator fun invoke(id :String): Book? {
        return repositoryLocal.getBookmarkById(id)
    }
}