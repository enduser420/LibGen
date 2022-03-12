package com.project.libgen.use_case.get_book_details

import com.project.libgen.core.util.Resource
import com.project.libgen.repository.LibGenDownloadRepository
import kotlinx.coroutines.flow.channelFlow
import retrofit2.HttpException
import javax.inject.Inject

class DownloadBookUseCase @Inject constructor(
    private val LibGenDownloadRepository: LibGenDownloadRepository
) {
    operator fun invoke(downloadLink: String) = channelFlow {
        try {
            send(Resource.Loading())
            val link = LibGenDownloadRepository.downloadBookLink(downloadLink)
            send(Resource.Success(link))
        } catch (e: Exception) {
            send(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: HttpException) {
            send(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}