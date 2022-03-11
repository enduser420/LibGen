package com.project.libgen.repository

import com.project.libgen.data.remote.BookDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Url

interface LibGenBookRepository {
    suspend fun getBookDetails(@Url bookId: String): BookDto
}