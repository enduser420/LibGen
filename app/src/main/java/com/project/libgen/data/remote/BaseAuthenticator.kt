package com.project.libgen.data.remote

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface BaseAuthenticator {
    suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser?
    suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser?
    suspend fun anonymousSignIn(): FirebaseUser?
    suspend fun googleSignIn(credential: AuthCredential): FirebaseUser?
    fun signOut()
}