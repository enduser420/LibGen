package com.project.libgen.repository

import com.project.libgen.data.model.Book
import org.jsoup.Jsoup

class LibGenSearchRepositoryImpl : LibGenSearchRepository {
    override suspend fun getBooks(query: String, filter: String): List<Book> {
        val bookList = mutableListOf<Book>()
        val doc =
            Jsoup.connect("https://libgen.rs/search.php?req=$query&res=100&column=$filter").get()
        val rows = doc.select("table.c").select("tr").drop(1)
        rows.forEach { item ->
            val id = item.child(0).text()
            val author = item.child(1).select("a").text()
            val title = item.child(2).select("a[title=\"\"]")[0].ownText()
            val publisher = item.child(3).ownText()
            val year = item.child(4).ownText()
            val pages = item.child(5).ownText()
            val language = item.child(6).ownText()
            val filesize = item.child(7).ownText()
            val extension = item.child(8).ownText()
            val downloadlink = item.child(9).select("a").attr("href")
            bookList.add(
                Book(
                    id = id,
                    author = author ?: null,
                    title = title ?: null,
                    publisher = publisher ?: null,
                    year = year ?: null,
                    pages = pages ?: null,
                    language = language ?: null,
                    extension = extension ?: null,
                    filesize = filesize ?: null,
                    downloadlink = downloadlink ?: null,
                    issn = null,
                    series = null,
                    coverurl = null,
                    descr = null,
                    volumeinfo = null,
                    torrent = null,
                    city = null,
                    edition = null
                )
            )
        }
        return bookList
    }
}