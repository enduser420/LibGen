package com.project.libgen.repository

import com.project.libgen.data.remote.BookDto


interface LibGenBookRepository {
    suspend fun getBookDetails(_id: String): BookDto
}