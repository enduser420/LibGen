package com.project.libgen.repository

import com.project.libgen.data.model.Book

interface LibGenSearchRepository {
    fun getBooks(query: String): List<Book>
}
