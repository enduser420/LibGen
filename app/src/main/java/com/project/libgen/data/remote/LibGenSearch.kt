package com.project.libgen.data.remote

import androidx.lifecycle.MutableLiveData
import com.project.libgen.data.model.Book
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ServiceScoped
import javax.inject.Inject
import javax.inject.Singleton

interface LibGenSearch {
    fun getBooks(query: String): List<Book>
}
