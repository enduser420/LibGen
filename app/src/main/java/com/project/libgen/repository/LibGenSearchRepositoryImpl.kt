package com.project.libgen.repository

import com.project.libgen.data.model.Book
import org.jsoup.Jsoup

class LibGenSearchRepositoryImpl : LibGenSearchRepository {
    override fun getBooks(query: String, filter: String): List<Book> {
        println("started scraping...")
        val bookList = mutableListOf<Book>()
        val doc = Jsoup.connect("https://libgen.rs/search.php?req=$query&res=100&column=$filter").get()
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
            bookList.add(
                Book(
                    id = id,
                    author = author,
                    title = title,
                    publisher = publisher,
                    year = year,
                    pages = pages,
                    language = language,
                    extension = extension,
                    filesize = filesize,
                    issn = "",
                    coverurl = "",
                    descr = "",
                    volumeinfo = "",
                    torrent = "",
                    city = "",
                    edition = ""
                )
            )
        }
        return bookList
    }
}