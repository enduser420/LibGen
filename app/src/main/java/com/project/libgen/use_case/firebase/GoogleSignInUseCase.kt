package com.project.libgen.use_case.firebase

import com.google.firebase.auth.AuthCredential
import com.project.libgen.core.util.Resource
import com.project.libgen.repository.AuthRepository
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(credential: AuthCredential) = channelFlow {
        try {
            send(Resource.Loading())
            val user = authRepository.googleSignIn(credential)
            send(Resource.Success(user))
        } catch (e: Exception) {
            send(
                Resource.Error(
                    message = e.localizedMessage ?: "Error."
                )
            )
        }
    }
}