package com.project.libgen.repository

import com.project.libgen.data.data_source.BookmarkDao
import com.project.libgen.data.model.Book
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao
) : BookmarkRepository {
    override suspend fun getBookmarks(userId: String): List<Book> {
        return bookmarkDao.getBooks(userId)
    }

    override suspend fun deleteAllBookmarks(userId: String) {
        return bookmarkDao.deleteAllBookmarks(userId)
    }

    override suspend fun insertBookmark(book: Book) {
        return bookmarkDao.addBook(book)
    }

    override suspend fun deleteBookmark(userId: String, bookId: String) {
        return bookmarkDao.deleteBook(userId, bookId)
    }

    override suspend fun getBookmarkBool(userId: String, bookId: String): Boolean {
        return bookmarkDao.getBookmarkBool(userId, bookId)
    }
}