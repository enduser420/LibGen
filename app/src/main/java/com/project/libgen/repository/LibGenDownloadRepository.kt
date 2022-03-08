package com.project.libgen.repository

interface LibGenDownloadRepository {
    suspend fun downloadBookLink(downloadLink: String): String?
}