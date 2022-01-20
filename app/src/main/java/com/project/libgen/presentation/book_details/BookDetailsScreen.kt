package com.project.libgen.presentation.book_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.project.libgen.R

@Composable
fun BookDetailsScreen() {
    ScreenContent()
}

@Composable
private fun ScreenContent(
    viewModel : BookDetailsViewModel = hiltViewModel()
) {
    println(viewModel.bookState.value)
    Scaffold {
        Column(modifier = Modifier.fillMaxSize()) {
            
        }
    }
}

@Preview
@Composable
fun Details() {
    ScreenContent()
}