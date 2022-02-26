package com.project.libgen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.project.libgen.presentation.book_details.BookDetailsScreen
import com.project.libgen.presentation.book_list.BookListScreen
import com.project.libgen.presentation.bookmark_details.BookmarkDetailsScreen
import com.project.libgen.presentation.bookmark_list.BookmarkListScreen
import com.project.libgen.presentation.user_login.UserLoginScreen
import com.project.libgen.presentation.user_signup.UserSignUpScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(
            route = Screen.UserLogin.route
        ) {
            UserLoginScreen(navController)
        }
        composable(
            route = Screen.UserSignUp.route
        ) {
            UserSignUpScreen(navController)
        }
        composable(
            route = Screen.BookList.route
        ) {
            BookListScreen(navController)
        }
        composable(
            route = Screen.BookDetails.route,
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
            route = Screen.BookmarkList.route
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