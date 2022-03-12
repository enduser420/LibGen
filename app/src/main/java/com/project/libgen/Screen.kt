package com.project.libgen

private const val book_id = "id"
private const val book_downloadlink = "downloadlink"
private const val book_md5 = "md5"

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

    object FictionBookDetails : Screen("fiction_book_details/{$book_md5}") {
        fun passMd5(md5: String): String {
            return "fiction_book_details/$md5"
        }
    }

    object BookmarkList : Screen("bookmark_list")
    object BookmarkDetails : Screen("bookmark_detail")
}