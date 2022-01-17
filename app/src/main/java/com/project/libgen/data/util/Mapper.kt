package com.project.libgen.data.util

interface Mapper<Book, BookDto> {

    fun mapFromDto(BookDto: BookDto) : Book

}