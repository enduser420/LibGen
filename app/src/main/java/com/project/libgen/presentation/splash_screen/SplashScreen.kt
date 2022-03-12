package com.project.libgen.presentation.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.libgen.R
import com.project.libgen.Screen
import com.project.libgen.presentation.components.util.UserState

@Composable
fun SplashScreen(
    navController: NavController
) {
    ScreenContent(navController)
}

@Composable
fun ScreenContent(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val currentUser by viewModel.currentUser.observeAsState(UserState())
    LaunchedEffect(key1 = currentUser, block = {
        if (currentUser.user == null) {
            navController.popBackStack()
            navController.navigate(Screen.UserLogin.route)
        } else {
            navController.popBackStack()
            navController.navigate(Screen.BookList.route)
        }
    })
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.app_icon),
            contentDescription = null
        )
        Text(
            text = "Library Genesis",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}