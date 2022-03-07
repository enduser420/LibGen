package com.project.libgen.repository

import org.jsoup.Jsoup

class LibGenDownloadRepositoryImpl : LibGenDownloadRepository {
    override fun downloadBookLink(downloadLink: String): String? {
        return try {
            val doc = Jsoup.connect(downloadLink)
                .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (HTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
                .timeout(1000)
                .get()
            return doc.select("div#download > ul > li > a").attr("href").toString()
        } catch (e: Exception) {
            null
        }
    }
}