package com.project.libgen.repository

import com.project.libgen.data.remote.BookDto
import com.project.libgen.data.remote.LibGenApi
import com.project.libgen.di.LibGenApiModule
import dagger.hilt.android.scopes.ServiceScoped
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Named


interface LibGenBookRepository {
    suspend fun getBookDetails(_id: String): BookDto
}