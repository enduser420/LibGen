package com.project.libgen.presentation.components

import java.util.regex.Pattern

class DisplayNameState: TextFieldState(
    validator = ::isDisplayNameValid,
    errorMessage = ::displayNameErrorMessage
)

private const val VALID_DISPLAY_NAME = "^[a-zA-z0-9\\s]+"
private fun isDisplayNameValid(displayName: String): Boolean {
    if (displayName.isEmpty()) return true
    return Pattern.matches(VALID_DISPLAY_NAME, displayName)
}
private fun displayNameErrorMessage(displayName: String) = "$displayName is invalid."