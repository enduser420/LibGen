package com.project.libgen.repository

interface LibGenDownloadRepository {
    fun downloadBookLink(downloadLink: String): String?
}