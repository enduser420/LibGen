package com.project.libgen.presentation.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ConfirmDialog(
    title: String,
    content: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = { Text(text = content) },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("No")
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Yes")
            }
        }
    )
}