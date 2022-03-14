package com.project.libgen.use_case.firebase

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.project.libgen.core.util.Resource
import com.project.libgen.repository.AuthRepository
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(_displayName: String, email: String, password: String) =
        channelFlow {
            try {
                send(Resource.Loading())
                val user = authRepository.signUpWithEmailPassword(email, password)
                user!!.updateProfile(userProfileChangeRequest {
                    displayName = _displayName
                })
                send(Resource.Success(user))
            } catch (e: Exception) {
                send(Resource.Error(e.localizedMessage ?: "Something went wrong."))
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                send(Resource.Error(e.localizedMessage ?: "Invalid Credentials"))
            }
        }
}