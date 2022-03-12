package com.project.libgen.repository

import com.project.libgen.data.model.Book

interface LibGenSearchRepository {
    suspend fun getBooks(query: String, filter: String): List<Book>
    suspend fun getFictionBooks(query: String): List<Book>
}
