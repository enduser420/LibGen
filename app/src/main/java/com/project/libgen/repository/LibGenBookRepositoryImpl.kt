package com.project.libgen.repository

import com.project.libgen.data.remote.BookDto
import com.project.libgen.data.remote.LibGenApi
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class LibGenBookRepositoryImpl @Inject constructor(
    private val api: LibGenApi
) : LibGenBookRepository {
    override suspend fun getBookDetails(bookId: String): BookDto {
        // here we get the first element, since we get a list from the API
        return api.getBookDetails(bookId)[0]
    }
}