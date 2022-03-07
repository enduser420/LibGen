package com.project.libgen.repository

import com.project.libgen.data.model.Book

interface LocalBookmarkRepository {
    suspend fun getBookmarks(): List<Book>
    suspend fun insertBookmark(book: Book)
    suspend fun getBookmarkById(id: String): Book?
    suspend fun getBookmarkBool(id: String): Boolean
    suspend fun deleteAllBookmarks()
    suspend fun deleteBookmark(book: Book)
}