package com.project.libgen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.project.libgen.presentation.book_details.BookDetailsScreen
import com.project.libgen.presentation.book_list.BookListScreen
import com.project.libgen.presentation.bookmark_details.BookmarkDetailsScreen
import com.project.libgen.presentation.bookmark_list.BookmarkListScreen
import com.project.libgen.presentation.splash_screen.SplashScreen
import com.project.libgen.presentation.user_login.UserLoginScreen
import com.project.libgen.presentation.user_signup.UserSignUpScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String,
    width: Int
) {
    AnimatedNavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(
            route = Screen.UserLogin.route,
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -width },
                    animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing)
                ) + fadeOut(animationSpec = tween(200))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -width },
                    animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(200))
            }
        ) {
            UserLoginScreen(navController)
        }
        composable(
            route = Screen.UserSignUp.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { width },
                    animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(200))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { width },
                    animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing)
                ) + fadeOut(animationSpec = tween(200))
            }
        ) {
            UserSignUpScreen(navController)
        }
        composable(
            route = Screen.BookList.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -width },
                    animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(200))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -width },
                    animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing)
                ) + fadeOut(animationSpec = tween(200))
            },
        ) {
            BookListScreen(navController)
        }
        composable(
            route = Screen.BookDetails.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { width },
                    animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(200))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { width },
                    animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing)
                ) + fadeOut(animationSpec = tween(200))
            },
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                },
                navArgument("downloadlink") {
                    type = NavType.StringType
                    defaultValue = ""
                })
        ) {
            BookDetailsScreen(navController)
        }
        composable(
            route = Screen.BookmarkList.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { width },
                    animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(200))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { width },
                    animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing)
                ) + fadeOut(animationSpec = tween(200))
            }
        ) {
            BookmarkListScreen(navController)
        }
        composable(
            route = Screen.BookmarkDetails.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {
            BookmarkDetailsScreen(navController)
        }
    }
}