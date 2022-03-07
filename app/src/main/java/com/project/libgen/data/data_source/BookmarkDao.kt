package com.project.libgen.data.data_source

import com.google.firebase.firestore.CollectionReference
import com.project.libgen.data.model.Book
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BookmarkDao @Inject constructor(
    private val dbCollection: CollectionReference
) : Dao {
    override suspend fun getBooks(userId: String): List<Book> {
        val bookmarks = mutableListOf<Book>()
        dbCollection
            .whereEqualTo("userId", userId)
            .get()
            .addOnCompleteListener {
                it.result.documents.forEach { doc ->
                    bookmarks.add(doc.toObject(Book::class.java)!!)
                }
            }.await()
        return bookmarks
    }

    override suspend fun deleteAllBookmarks(userId: String) {
        dbCollection
            .whereEqualTo("userId", userId)
            .get()
            .await()
            .forEach { doc ->
                doc.reference.delete()
            }
    }

    override suspend fun getBookmarkBool(userId: String, bookId: String): Boolean {
        var bookmarked = false
        dbCollection
            .whereEqualTo("userId", userId)
            .whereEqualTo("id", bookId)
            .get()
            .await()
            .forEach {
                bookmarked = it.getBoolean("bookmarked") == true
            }
        return bookmarked
    }

    override suspend fun addBook(book: Book) {
        dbCollection.add(book)
    }

    override suspend fun deleteBook(userId: String, bookId: String) {
        dbCollection
            .whereEqualTo("userId", userId)
            .whereEqualTo("id", bookId)
            .get()
            .await()
            .forEach { doc ->
                doc.reference.delete()
            }

    }
}