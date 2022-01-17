package com.project.libgen

const val book_id = "id"

sealed class Screen(val route: String) {
    object BookList: Screen("book_list")
    object BookDetials: Screen("book_details/{$book_id}") {
        fun passId(id: String): String    {
            return this.route.replace(oldValue = "{$book_id}", newValue = id)
        }
    }
}