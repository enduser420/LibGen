package com.project.libgen.use_case.bookmark

data class BookmarkUseCases(
    val getLocalBookmarks: GetLocalBookmarks,
    val getBookmarks: GetBookmarks,
    val getLocalBookmark: GetLocalBookmark,
    val insertLocalBookmark: InsertLocalBookmark,
    val deleteLocalBookmark: DeleteLocalBookmark,
    val deleteAllLocalBookmarks: DeleteAllLocalBookmarks,
    val deleteAllBookmarks: DeleteAllBookmarks,
    val getLocalBookmarkBool: GetLocalBookmarkBool,
    val getBookmarkBool: GetBookmarkBool
)