package com.project.libgen.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LibGenApi {
    @GET("json.php?fields=id,author,title,torrent,publisher,year,extension,descr,coverurl,series,volumeinfo,city,edition,pages,language,issn,filesize")
    suspend fun getBookDetails(
        @Query("ids") bookId: String
    ): List<BookDto>
}