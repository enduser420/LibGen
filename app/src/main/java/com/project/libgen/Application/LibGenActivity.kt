package com.project.libgen.Application

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.libgen.BookList.BookListViewModel
import com.project.libgen.SetupNavGraph
import com.project.libgen.data.model.Book
import com.project.libgen.repository.LibGenSearchImpl
import com.project.libgen.ui.theme.LibGenTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private lateinit var viewModel: BookListViewModel

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        super.onCreate(savedInstanceState)
        setContent {
            LibGenTheme {
                navController = rememberNavController()
                viewModel = BookListViewModel(LibGenSearchImpl())
                SetupNavGraph(navController = navController, viewModel = viewModel)
            }
        }
    }
}