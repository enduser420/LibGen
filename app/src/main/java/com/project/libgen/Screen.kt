package com.project.libgen

const val book_id = "id"
const val book_downloadlink = "downloadlink"

sealed class Screen(val route: String) {
    object BookList: Screen("book_list")
    object BookDetails: Screen("book_details/{$book_id}/{$book_downloadlink}") {
        fun passIdandLink(id: String, downloadlink: String = ""): String {
            return "book_details/$id/$downloadlink"
        }
    }
    object BookmarkList: Screen("bookmark_list")
    object BookmarkDetails: Screen("bookmark_detail") {
        fun passId(id: String): String {
            return this.route.replace(oldValue = "{$book_id}", newValue = id)
        }
    }
}