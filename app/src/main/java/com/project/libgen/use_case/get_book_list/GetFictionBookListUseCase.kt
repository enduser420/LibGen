package com.project.libgen.use_case.get_book_list

import com.project.libgen.core.util.Resource
import com.project.libgen.repository.LibGenSearchRepository
import kotlinx.coroutines.flow.channelFlow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFictionBookListUseCase @Inject constructor(
    private val LibGenSearch: LibGenSearchRepository
) {
    operator fun invoke(searchQuery: String) = channelFlow {
        try {
            send(Resource.Loading())
            val bookList = LibGenSearch.getFictionBooks(searchQuery)
            send(Resource.Success(bookList))
        } catch (e: HttpException) {
            send(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            send(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}