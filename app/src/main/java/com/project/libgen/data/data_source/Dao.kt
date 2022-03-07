package com.project.libgen.data.data_source

import com.project.libgen.data.model.Book

interface Dao {
    suspend fun getBooks(userId: String): List<Book>
    suspend fun addBook(book: Book)
    suspend fun deleteBook(userId: String, bookId: String)
    suspend fun getBookmarkBool(userId: String, bookId: String): Boolean
    suspend fun deleteAllBookmarks(userId: String)
}