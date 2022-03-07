package com.project.libgen.use_case.get_book_details

import com.project.libgen.core.util.Resource
import com.project.libgen.data.remote.toBook
import com.project.libgen.repository.LibGenBookRepository
import kotlinx.coroutines.flow.channelFlow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBookDetailsUseCase @Inject constructor(
    private val LibGenBookRepository: LibGenBookRepository
) {
    operator fun invoke(bookId: String) = channelFlow {
        try {
            send(Resource.Loading())
            val bookDetails = LibGenBookRepository.getBookDetails(bookId).toBook()
            send(Resource.Success(bookDetails))
        } catch (e: HttpException) {
            send(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            send(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}