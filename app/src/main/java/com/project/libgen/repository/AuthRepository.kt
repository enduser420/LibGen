package com.project.libgen.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser?
    suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser?
    suspend fun anonymousSignUp() : FirebaseUser?
    suspend fun googleSignIn(credential: AuthCredential): FirebaseUser?
    fun signOut()
}