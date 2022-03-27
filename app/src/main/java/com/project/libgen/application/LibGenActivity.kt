package com.project.libgen.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.project.libgen.Screen
import com.project.libgen.SetupNavGraph
import com.project.libgen.ui.theme.LibGenTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibGenTheme {
                val configuration = LocalConfiguration.current
                navController = rememberAnimatedNavController()
                SetupNavGraph(
                    navController = navController,
                    startDestination = Screen.BookList.route,
                    width = configuration.screenWidthDp / 2
                )
            }
        }
    }
}