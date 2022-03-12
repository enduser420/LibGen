package com.project.libgen.di

import com.project.libgen.repository.LibGenSearchRepository
import com.project.libgen.repository.LibGenSearchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LibGenSearchRepositoryModule {

    @Singleton
    @Provides
    fun provideLibGenSearch(): LibGenSearchRepository {
        return LibGenSearchRepositoryImpl()
    }
}