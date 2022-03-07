package com.project.libgen.use_case.bookmark

data class BookmarkUseCases(
    val getLocalBookmarks: GetLocalBookmarks,
    val getBookmarks: GetBookmarks,
    val getBookmark: GetBookmark,
    val insertBookmark: InsertBookmark,
    val deleteBookmark: DeleteBookmark,
    val deleteAllLocalBookmarks: DeleteAllLocalBookmarks,
    val deleteAllBookmarks: DeleteAllBookmarks,
    val getLocalBookmarkBool: GetLocalBookmarkBool,
    val getBookmarkBool: GetBookmarkBool
)