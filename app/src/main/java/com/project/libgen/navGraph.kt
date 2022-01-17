package com.project.libgen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.project.libgen.presentation.book_details.BookDetailsScreen
import com.project.libgen.presentation.book_list.BookListScreen

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
            route = Screen.BookDetials.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {
            println(it.arguments?.getString("id"))
            BookDetailsScreen()
        }
    }
}