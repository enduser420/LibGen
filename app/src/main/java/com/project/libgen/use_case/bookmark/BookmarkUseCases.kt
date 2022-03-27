package com.project.libgen.use_case.bookmark

data class BookmarkUseCases(
    val getLocalBookmarks: GetLocalBookmarks,
    val getLocalBookmark: GetLocalBookmark,
    val insertLocalBookmark: InsertLocalBookmark,
    val deleteLocalBookmark: DeleteLocalBookmark,
    val deleteAllLocalBookmarks: DeleteAllLocalBookmarks,
    val getLocalBookmarkBool: GetLocalBookmarkBool
)