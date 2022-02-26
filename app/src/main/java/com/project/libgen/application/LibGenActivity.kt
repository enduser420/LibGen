package com.project.libgen.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.libgen.SetupNavGraph
import com.project.libgen.presentation.user_login.UserLogInViewModel
import com.project.libgen.presentation.user_signup.UserSignUpViewModel
import com.project.libgen.ui.theme.LibGenTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    private val viewModel by viewModels<UserLogInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibGenTheme {
                val screen by viewModel.startDestination
                navController = rememberNavController()
                SetupNavGraph(navController = navController, startDestination = screen)
            }
        }
    }
}