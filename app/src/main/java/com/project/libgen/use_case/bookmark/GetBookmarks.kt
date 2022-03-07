package com.project.libgen.use_case.bookmark

import com.project.libgen.core.util.Resource
import com.project.libgen.repository.BookmarkRepository
import kotlinx.coroutines.flow.channelFlow

class GetBookmarks(
    private val repository: BookmarkRepository
) {
    operator fun invoke(userId: String) = channelFlow {
        try {
            send(Resource.Loading())
            val bookmarkList = repository.getBookmarks(userId)
            send(Resource.Success(bookmarkList))
        } catch (e: Exception) {
            send(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}