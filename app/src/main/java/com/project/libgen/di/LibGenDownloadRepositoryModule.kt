package com.project.libgen.di

import android.app.DownloadManager
import android.content.Context
import com.project.libgen.repository.LibGenBookRepository
import com.project.libgen.repository.LibGenDownloadRepository
import com.project.libgen.repository.LibGenDownloadRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

//    @Singleton
//    @Provides
//    fun provideDownloadManager(@ApplicationContext appContext: Context): DownloadManager {
//        return DownloadManager.
//    }
}