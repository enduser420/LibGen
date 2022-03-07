package com.project.libgen.use_case.bookmark

import com.project.libgen.data.model.Book
import com.project.libgen.repository.LocalBookmarkRepository

class InsertBookmark(
    private val repositoryLocal: LocalBookmarkRepository
) {

    suspend operator fun invoke(book: Book?) {
        book?.let {
            repositoryLocal.insertBookmark(book)
        }
    }
}