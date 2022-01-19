package com.project.libgen.repository

import com.project.libgen.data.remote.BookDto
import com.project.libgen.data.remote.LibGenApi
import javax.inject.Inject

class LibGenBookRepositoryImpl @Inject constructor(
    private val api: LibGenApi
) : LibGenBookRepository {
    override suspend fun getBookDetails(_id: String): BookDto {
        return api.getBookDetails(_id)
    }
}