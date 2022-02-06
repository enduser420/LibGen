package com.project.libgen.use_case.get_book_details

import com.project.libgen.core.util.Resource
import com.project.libgen.data.model.Book
import com.project.libgen.data.remote.toBook
import com.project.libgen.repository.LibGenBookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBookDetailsUseCase @Inject constructor(
    private val LibGenBookRepository: LibGenBookRepository
) {
    operator fun invoke(bookId: String): Flow<Resource<Book>> = flow {
        try {
            emit(Resource.Loading())
            val bookDetails = LibGenBookRepository.getBookDetails(bookId).toBook()
            emit(Resource.Success(bookDetails))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}