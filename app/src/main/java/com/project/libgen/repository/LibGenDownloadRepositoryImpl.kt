package com.project.libgen.repository

import org.jsoup.Jsoup

class LibGenDownloadRepositoryImpl : LibGenDownloadRepository {
    override suspend fun downloadBookLink(downloadLink: String): List<String> {
        val downloadlinks = mutableListOf<String>()
        val doc = Jsoup.connect(downloadLink)
            .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (HTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
            .get()
        downloadlinks.add(doc.select("div#download > h2 > a").attr("href"))
        downloadlinks.add(doc.select("div#download > ul > li:nth-child(1) > a").attr("href"))
        downloadlinks.add(doc.select("div#download > ul > li:nth-child(2) > a").attr("href"))
        return downloadlinks
    }
}