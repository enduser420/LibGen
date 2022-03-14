package com.project.libgen.repository

import com.project.libgen.data.model.Book
import com.project.libgen.data.remote.BookDto
import com.project.libgen.data.remote.LibGenApi
import org.jsoup.Jsoup
import javax.inject.Inject

class LibGenBookRepositoryImpl @Inject constructor(
    private val api: LibGenApi
) : LibGenBookRepository {
    override suspend fun getBookDetails(bookId: String): BookDto {
        // here we get the first element, since we get a list from the API
        return api.getBookDetails(bookId)[0]
    }

    override suspend fun getFictionBookDetails(md5: String): Book {
        val doc = Jsoup
            .connect("https://libgen.rs/fiction/$md5")
            .get()
        val title =
            doc.select("table.record > tbody > tr:nth-child(1) > td.record_title").text()
        val author =
            doc.select("table.record > tbody > tr:nth-child(2) > td:nth-child(2) > ul.catalog_authors > li > a")
                .text()
        val language =
            doc.select("table.record > tbody > tr:nth-child(3) > td:nth-child(2)").text()
        val year =
            doc.select("table.record > tbody > tr:nth-child(4) > td:nth-child(2)").text()
        val _md5 = doc.select("table.hashes > tbody >tr:nth-child(1) >td ").text()
        val coverurl = doc.select("div.record_side").select("img").attr("src")
        val torrent = doc.select("ul.record_mirrors > li:last-child").select("a").attr("href")
        val downloadlink =
            doc.select("ul.record_mirrors > li:nth-child(1)").select("a").attr("href")
        return Book(
            id = "",
            title = title,
            author = author,
            coverurl = "https://libgen.rs$coverurl",
            year = year,
            md5 = _md5,
            language = language,
            downloadlink = downloadlink,
            torrent = torrent
        )
    }
}