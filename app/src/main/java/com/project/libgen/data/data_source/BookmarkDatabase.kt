package com.project.libgen.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.libgen.data.model.Book

@Database(
    entities = [Book::class],
    version = 2
)

abstract class BookmarkDatabase : RoomDatabase() {

    abstract val localBookmarkDao: LocalBookmarkDao

    companion object {
        const val DATABASE_NAME = "bookmark_db"
    }
}