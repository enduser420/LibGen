package com.project.libgen.data.remote

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseAuthenticator : BaseAuthenticator {
    override suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser? {
        return Firebase.auth.createUserWithEmailAndPassword(email, password).await().user
    }

    override suspend fun googleSignIn(credential: AuthCredential): FirebaseUser? {
        return Firebase.auth.signInWithCredential(credential).await().user
    }

    override suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser? {
        return Firebase.auth.signInWithEmailAndPassword(email, password).await().user
    }

    override suspend fun anonymousSignIn(): FirebaseUser? {
        return Firebase.auth.signInAnonymously().await().user
    }

    override fun signOut() {
        Firebase.auth.signOut()
    }
}