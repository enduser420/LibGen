package com.project.libgen.repository

import com.project.libgen.data.remote.BookDto
import com.project.libgen.data.remote.LibGenApi
import dagger.hilt.android.scopes.ServiceScoped
import retrofit2.Call
import javax.inject.Inject


class LibGenApiImpl @Inject constructor(
    private val api: LibGenApi
) : LibGenApi {
    override suspend fun getBookDetails(_id: String): BookDto {
        return api.getBookDetails(_id)
    }
}