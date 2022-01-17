package com.project.libgen.di

import com.project.libgen.data.remote.LibGenSearch
import com.project.libgen.repository.LibGenSearchImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LibGenSearchModule {

    @Singleton
    @Provides
    fun provideLibGenSearch() : LibGenSearch {
        return LibGenSearchImpl()
    }
}