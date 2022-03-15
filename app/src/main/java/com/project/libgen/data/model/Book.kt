package com.project.libgen.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.libgen.presentation.components.util.Mode

@Entity
data class Book(
    @PrimaryKey var id: String = "",
    val author: String? = null,
    val title: String? = null,
    val pages: String? = null,
    val coverurl: String? = null,
    var series: String? = null,
    var extension: String? = null,
    val publisher: String? = null,
    val year: String? = null,
    val descr: String? = null,
    val issn: String? = null,
    val volumeinfo: String? = null,
    var md5: String? = null,
    val torrent: String? = null,
    val city: String? = null,
    val edition: String? = null,
    var language: String? = null,
    var filesize: String? = null,
    var mode: Mode? = null,
    var userId: String? = null,
    var downloadlink: String? = null, // var because this needs to be changed in BookDetailsViewModel
    @ColumnInfo(name = "bookmarked") var bookmarked: Boolean = false // ^
)
