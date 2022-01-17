package com.project.libgen.repository

import com.project.libgen.data.model.Book
import com.project.libgen.data.remote.LibGenSearch
import org.jsoup.Jsoup

class LibGenSearchImpl : LibGenSearch {
    override fun getBooks(query: String): MutableList<Book> {
        val bookList: MutableList<Book> = mutableListOf()
        val doc = Jsoup.connect("https://libgen.rs/search.php?req=$query&res=50").get()
        val rows = doc.select("table.c").select("tr").drop(1)
        rows.forEach { item ->
            val id = item.child(0).text()
            val author = item.child(1).select("a").text()
            val title = item.child(2).text()
            val publisher = item.child(3).text()
            val year = item.child(4).text()
            val pages = item.child(5).text()
            val language = item.child(6).text()
            val filesize = item.child(7).text()
            val extension = item.child(8).text()

            val book = Book(
                id = id,
                author = author,
                title = title,
                publisher = publisher,
                year = year,
                pages = pages,
                language = language,
                extension = extension,
                filesize = filesize

            )
            bookList.add(book)
        }
        return bookList
    }

}