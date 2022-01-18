package com.project.libgen.data.remote

import com.project.libgen.data.model.Book

interface LibGenSearch {
    fun getBooks(query: String): List<Book>
}
