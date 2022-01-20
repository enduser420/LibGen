package com.project.libgen.repository

import com.project.libgen.data.remote.BookDto
import retrofit2.Response


interface LibGenBookRepository {
    suspend fun getBookDetails(bookId: String): BookDto
}