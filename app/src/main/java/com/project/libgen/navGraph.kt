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

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.BookList.route) {
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