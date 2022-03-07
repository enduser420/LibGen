package com.project.libgen.data.data_source

import androidx.room.*
import androidx.room.Dao
import com.project.libgen.data.model.Book

@Dao
interface LocalBookmarkDao {

    @Query("SELECT * FROM book")
    suspend fun getLocalBookmarks(): List<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalBookmark(book: Book)

    @Query("SELECT * FROM book WHERE id = :id")
    suspend fun getLocalBookmarkById(id: String): Book?

    @Query("SELECT bookmarked FROM book WHERE id = :id")
    suspend fun getLocalBookmarkBool(id: String): Boolean

    @Query("DELETE FROM book")
    suspend fun deleteAllLocalBookmarks()

    @Delete
    suspend fun deleteLocalBookmark(book: Book)
}