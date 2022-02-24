package com.project.libgen.repository

import com.project.libgen.data.remote.BookDto
import retrofit2.http.Url

interface LibGenBookRepository {
    suspend fun getBookDetails(@Url bookId: String): BookDto
}