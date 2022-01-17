package com.project.libgen.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LibGenApi {

    @GET("json.php")
    suspend fun getBookDetails(
        @Query("ids={_id}&fields=id,author,title,torrent,publisher,year,extension,descr,coverurl,issn,series,volumeinfo,city,edition,pages,language,issn,filesize") _id: String
    ): BookDto
}