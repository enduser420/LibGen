package com.project.libgen

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.project.libgen.BookDetails.BookDetailsScreen
import com.project.libgen.BookList.BookListScreen
import com.project.libgen.BookList.BookListViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: BookListViewModel
) {
    NavHost(navController = navController, startDestination = Screen.BookList.route ) {
        composable(
            route = Screen.BookList.route
        ) {
            BookListScreen(navController = navController, viewModel = viewModel)
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