package com.project.libgen.presentation.components

import android.util.Patterns

class EmailState : TextFieldState(
    validator = ::isEmailValid,
    errorMessage = ::emailErrorMessage
)

private fun isEmailValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
private fun emailErrorMessage(email: String) = "Email $email is invalid."