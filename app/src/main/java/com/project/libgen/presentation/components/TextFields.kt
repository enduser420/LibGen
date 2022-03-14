package com.project.libgen.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun DisplayName(displayNameState: DisplayNameState, label: String = "Display Name") {
    Column {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = displayNameState.text,
            onValueChange = {
                displayNameState.text = it
                displayNameState.validate()
            },
            label = {
                Text(text = label)
            },
            isError = displayNameState.error != null
        )
        displayNameState.error?.let { ErrorField(it) }
    }
}

@Composable
fun Email(emailState: EmailState, label: String = "Email") {
    Column {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = emailState.text,
            onValueChange = {
                emailState.text = it
                emailState.validate()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = {
                Text(text = label)
            },
            isError = emailState.error != null
        )
        emailState.error?.let { ErrorField(it) }
    }
}

@Composable
fun Password(passwordState: PasswordState, label: String = "Password") {
    var passwordVisibility by remember { mutableStateOf(false) }
    Column {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = passwordState.text,
            onValueChange = {
                passwordState.text = it
                passwordState.validate()
            },
            label = {
                Text(text = label)
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisibility)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            isError = passwordState.error != null
        )
        passwordState.error?.let { ErrorField(it) }
    }
}

@Composable
fun ErrorField(error: String) {
    Text(
        text = error,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(color = MaterialTheme.colors.error)
    )
}
