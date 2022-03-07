package com.project.libgen.use_case.bookmark

import com.project.libgen.repository.LocalBookmarkRepository

class GetLocalBookmarkBool(
    private val repositoryLocal: LocalBookmarkRepository
) {
    suspend operator fun invoke(id: String): Boolean {
        return repositoryLocal.getBookmarkBool(id)
    }
}