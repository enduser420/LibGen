package com.project.libgen.repository

import com.project.libgen.data.data_source.LocalBookmarkDao
import com.project.libgen.data.model.Book
import javax.inject.Inject

class LocalBookmarkRepositoryImpl @Inject constructor(
    private val LocalBookmarkDao: LocalBookmarkDao
) : LocalBookmarkRepository {
    override suspend fun getBookmarks(): List<Book> {
        return LocalBookmarkDao.getLocalBookmarks()
    }

    override suspend fun insertBookmark(book: Book) {
        return LocalBookmarkDao.insertLocalBookmark(book)
    }

    override suspend fun getBookmarkById(id: String): Book? {
        return LocalBookmarkDao.getLocalBookmarkById(id)
    }

    override suspend fun getBookmarkBool(id: String): Boolean {
        return LocalBookmarkDao.getLocalBookmarkBool(id)
    }

    override suspend fun deleteAllBookmarks() {
        return LocalBookmarkDao.deleteAllLocalBookmarks()
    }

    override suspend fun deleteBookmark(book: Book) {
        return LocalBookmarkDao.deleteLocalBookmark(book)
    }
}