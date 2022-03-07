package com.project.libgen.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.project.libgen.data.remote.BaseAuthenticator
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authenticator: BaseAuthenticator
) : AuthRepository {
    override suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser? {
        return authenticator.signInWithEmailPassword(email, password)
    }

    override suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser? {
        return authenticator.signUpWithEmailPassword(email, password)
    }

    override suspend fun anonymousSignUp(): FirebaseUser? {
        return authenticator.anonymousSignIn()
    }

    override suspend fun googleSignIn(credential: AuthCredential): FirebaseUser? {
        return authenticator.googleSignIn(credential)
    }

    override fun signOut() {
        return authenticator.signOut()
    }
}