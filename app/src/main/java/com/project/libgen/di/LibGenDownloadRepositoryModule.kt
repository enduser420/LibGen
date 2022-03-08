package com.project.libgen.di

import com.project.libgen.repository.LibGenDownloadRepository
import com.project.libgen.repository.LibGenDownloadRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LibGenDownloadRepositoryModule {
    @Singleton
    @Provides
    fun provideLibGenBookDownload(): LibGenDownloadRepository {
        return LibGenDownloadRepositoryImpl()
    }
}