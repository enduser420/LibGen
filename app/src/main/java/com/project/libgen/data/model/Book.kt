package com.project.libgen.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    @PrimaryKey val id: String = "",
    val author: String? = "",
    val title: String? = "",
    val pages: String? = "",
    val coverurl: String? = "",
    val series: String? = "",
    val extension: String? = "",
    val publisher: String? = "",
    val year: String? = "",
    val descr: String? = "",
    val issn: String? = "",
    val volumeinfo: String? = "",
    val torrent: String? = "",
    val city: String? = "",
    val edition: String? = "",
    val language: String? = "",
    val filesize: String? = "",
    var userId: String? = "",
    var downloadlink: String? = "", // var because this needs to be changed in BookDetailsViewModel
    @ColumnInfo(name = "bookmarked") var bookmarked: Boolean = false // ^
)
