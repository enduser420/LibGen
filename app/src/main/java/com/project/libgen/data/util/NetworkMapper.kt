package com.project.libgen.data.util

import com.project.libgen.data.model.Book
import com.project.libgen.data.remote.BookDto
import javax.inject.Inject

class NetworkMapper
@Inject constructor() : Mapper<Book, BookDto> {
    override fun mapFromDto(BookDto: BookDto): Book {
        return Book(
            id = BookDto.id,
            author = BookDto.author,
            title = BookDto.title,
            pages = BookDto.pages,
            extension = BookDto.extension,
            coverurl = BookDto.coverurl,
            publisher = BookDto.publisher,
            year = BookDto.year,
            descr = BookDto.descr,
            issn = BookDto.issn,
            volumeinfo = BookDto.volumeinfo,
            torrent = BookDto.torrent,
            city = BookDto.city,
            edition = BookDto.edition,
            language = BookDto.language,
            filesize = BookDto.filesize
        )
    }
}