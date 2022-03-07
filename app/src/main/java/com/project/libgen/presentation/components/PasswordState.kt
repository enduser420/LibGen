package com.project.libgen.presentation.components

class PasswordState : TextFieldState(
    validator = ::isPasswordValid,
    errorMessage = { passwordErrorMessage() }
)

private fun isPasswordValid(password: String): Boolean = password.length >= 5
private fun passwordErrorMessage() = "Password is invalid."