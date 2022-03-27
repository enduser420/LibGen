package com.project.libgen.di

import android.app.Application
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.project.libgen.data.data_source.BookmarkDatabase
import com.project.libgen.repository.LocalBookmarkRepository
import com.project.libgen.repository.LocalBookmarkRepositoryImpl
import com.project.libgen.use_case.bookmark.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BookmarkRepositoryModule {

    @Provides
    @Singleton
    fun provideBookmarkDatabase(app: Application): BookmarkDatabase {
        return Room.databaseBuilder(
            app,
            BookmarkDatabase::class.java,
            BookmarkDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideLocalBookmarkRepository(db: BookmarkDatabase): LocalBookmarkRepository {
        return LocalBookmarkRepositoryImpl(db.localBookmarkDao)
    }

    @Provides
    @Singleton
    fun provideBookmarkUseCases(
        localBookmarkRepository: LocalBookmarkRepository
    ): BookmarkUseCases {
        return BookmarkUseCases(
            getLocalBookmarks = GetLocalBookmarks(localBookmarkRepository),
            deleteLocalBookmark = DeleteLocalBookmark(localBookmarkRepository),
            insertLocalBookmark = InsertLocalBookmark(localBookmarkRepository),
            getLocalBookmark = GetLocalBookmark(localBookmarkRepository),
            getLocalBookmarkBool = GetLocalBookmarkBool(localBookmarkRepository),
            deleteAllLocalBookmarks = DeleteAllLocalBookmarks(localBookmarkRepository)
        )
    }

    @Provides
    @Singleton
    fun provideDBCollection() = FirebaseFirestore.getInstance()
        .collection("Books")

}
