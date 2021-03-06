package com.project.libgen.use_case.firebase

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.project.libgen.core.util.Resource
import com.project.libgen.repository.AuthRepository
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(email: String, password: String) =
        channelFlow {
            try {
                send(Resource.Loading())
                val user = authRepository.signInWithEmailPassword(email, password)
                send(Resource.Success(user))
            } catch (e: Exception) {
                send(Resource.Error(e.localizedMessage ?: "Something went wrong."))
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                send(Resource.Error(e.localizedMessage ?: "Invalid Credentials"))
            }
        }

    operator fun invoke() = channelFlow {
        try {
            send(Resource.Loading())
            val user = authRepository.anonymousSignUp()
            send(Resource.Success(user))
        } catch (e: Exception) {
            send(Resource.Error(e.localizedMessage ?: "Something went wrong."))
        }
    }
}