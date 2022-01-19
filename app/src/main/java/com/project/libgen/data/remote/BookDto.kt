package com.project.libgen.data.remote


import com.google.gson.annotations.SerializedName
import com.project.libgen.data.model.Book

data class BookDto(
    @SerializedName("id")
    var id: String,
    @SerializedName("author")
    var author: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("torrent")
    var torrent: String,
    @SerializedName("publisher")
    var publisher: String,
    @SerializedName("year")
    var year: String,
    @SerializedName("extension")
    var extension: String,
    @SerializedName("descr")
    var descr: String,
    @SerializedName("coverurl")
    var coverurl: String,
    @SerializedName("issn")
    var issn: String,
    @SerializedName("series")
    var series: String,
    @SerializedName("volumeinfo")
    var volumeinfo: String,
    @SerializedName("city")
    var city: String,
    @SerializedName("edition")
    var edition: String,
    @SerializedName("pages")
    var pages: String,
    @SerializedName("language")
    var language: String,
    @SerializedName("filesize")
    var filesize: String
)

fun BookDto.toBook() : Book {
    return Book(
        id = id,
        author = author,
        title = title,
        pages = pages,
        coverurl = coverurl,
        extension = extension,
        publisher = publisher,
        year = year,
        descr = descr,
        issn = issn,
        volumeinfo = volumeinfo,
        torrent = torrent,
        city = city,
        edition = edition,
        language = language,
        filesize = filesize
    )
}