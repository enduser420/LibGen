package com.project.libgen.repository

import com.project.libgen.data.model.Book

interface BookmarkRepository {
    suspend fun getBookmarks(userId: String): List<Book>
    suspend fun insertBookmark(book: Book)
    suspend fun getBookmarkBool(userId: String, bookId: String): Boolean
    suspend fun deleteAllBookmarks(userId: String)
    suspend fun deleteBookmark(userId: String, bookId: String)
}