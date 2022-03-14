package com.project.libgen

private const val book_id = "id"
private const val book_downloadlink = "downloadlink"
private const val book_md5 = "md5"
private const val book_series = "series"
private const val book_language = "language"
private const val book_extension = "extension"
private const val book_filesize = "filesize"

private const val mode = "mode"

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object UserLogin : Screen("user_login")
    object UserSignUp : Screen("user_signup")
    object BookList : Screen("book_list")
    object BookDetails : Screen("book_details/{$book_id}/{$book_downloadlink}") {
        fun passIdandLink(id: String, downloadlink: String): String {
            return "book_details/$id/$downloadlink"
        }
    }

    object FictionBookDetails :
        Screen("fiction_book_details/{$book_md5}/{$book_downloadlink}/{$book_series}/{$book_language}/{$book_extension}/{$book_filesize}") {
        fun passMd5andLinkandOthers(
            md5: String,
            downloadlink: String,
            language: String,
            series: String,
            extension: String,
            filesize: String
        ): String {
            return "fiction_book_details/$md5/$downloadlink/$series/$language/$extension/$filesize"
        }
    }

    object UserSettingsScreen : Screen("user_settings/{$mode}") {
        fun passMode(mode: String): String {
            return "user_settings/$mode"
        }
    }

    object BookmarkList : Screen("bookmark_list")
}