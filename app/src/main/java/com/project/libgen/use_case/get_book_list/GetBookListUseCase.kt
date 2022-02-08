package com.project.libgen.use_case.get_book_list

import com.project.libgen.core.util.Resource
import com.project.libgen.data.model.Book
import com.project.libgen.repository.LibGenSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBookListUseCase @Inject constructor(
    private val LibGenSearch: LibGenSearchRepository
) {
    operator fun invoke(searchQuery: String, filterOption: String): Flow<Resource<List<Book>>> =
        flow {
            try {
                emit(Resource.Loading())
                val bookList = LibGenSearch.getBooks(searchQuery, filterOption)
                emit(Resource.Success(bookList))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
}