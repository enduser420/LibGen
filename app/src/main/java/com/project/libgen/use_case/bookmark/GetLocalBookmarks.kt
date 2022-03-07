package com.project.libgen.use_case.bookmark

import com.project.libgen.core.util.Resource
import com.project.libgen.repository.LocalBookmarkRepository
import kotlinx.coroutines.flow.channelFlow

class GetLocalBookmarks(
    private val localRepository: LocalBookmarkRepository
) {
    operator fun invoke() = channelFlow {
        try {
            send(Resource.Loading())
            val bookmarkList = localRepository.getBookmarks()
            send(Resource.Success(bookmarkList))
        } catch (e: Exception) {
            send(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}