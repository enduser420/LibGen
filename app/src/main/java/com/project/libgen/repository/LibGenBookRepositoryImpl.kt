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
        val id = doc.select("table.record > tbody > tr:nth-child(9) > td:nth-child(2)")[0].ownText()
        val title =
            doc.select("table.record > tbody > tr:nth-child(1) > td.record_title")[0].ownText()
        val author =
            doc.select("table.record > tbody > tr:nth-child(2) > td:nth-child(2) > ul.catalog_authors > li > a").text()
        val language =
            doc.select("table.record > tbody > tr:nth-child(3) > td:nth-child(2)")[0].ownText()
        val year =
            doc.select("table.record > tbody > tr:nth-child(4) > td:nth-child(2)")[0].ownText()
        val publisher =
            doc.select("table.record > tbody > tr:nth-child(5) > td:nth-child(2)")[0].ownText()
        val isbn =
            doc.select("table.record > tbody > tr:nth-child(6) > td:nth-child(2)")[0].ownText()
        val extension =
            doc.select("table.record > tbody > tr:nth-child(7) > td:nth-child(2)")[0].ownText()
        val filesize =
            doc.select("table.record > tbody > tr:nth-child(8) > td:nth-child(2)")[0].ownText()
        val md5 = doc.select("table.hashes > tbody >tr:nth-child(1) >td ")[0].ownText()
        val coverurl = doc.select("div.record_side").select("img").attr("src")
        return Book(
            id = id,
            title = title,
            author = author,
            coverurl = coverurl,
            extension = extension,
            publisher = publisher,
            year = year,
            issn = isbn,
            md5 = md5,
            language = language,
            filesize = filesize
        )
    }
}