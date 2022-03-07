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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.libgen.R
import com.project.libgen.Screen

@Composable
fun SplashScreen(
    navController: NavController
) {
    ScreenContent(navController)
}

@Composable
fun ScreenContent(
    navController: NavController
) {
    LaunchedEffect(key1 = true, block = {
        Firebase.auth.currentUser.let {
            if (it == null) {
                navController.popBackStack()
                navController.navigate(Screen.UserLogin.route)
            } else {
                navController.popBackStack()
                navController.navigate(Screen.BookList.route)
            }
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
            text = "LibGen",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}