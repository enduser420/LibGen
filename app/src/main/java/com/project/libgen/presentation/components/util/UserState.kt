package com.project.libgen.presentation.components.util

import com.google.firebase.auth.FirebaseUser

data class UserState(
    val isLoading: Boolean = false,
    val user: FirebaseUser? = null,
    val error: String = ""
)