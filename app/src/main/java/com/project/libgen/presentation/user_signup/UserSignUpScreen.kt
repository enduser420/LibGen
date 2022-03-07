package com.project.libgen.presentation.user_signup

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.project.libgen.Screen
import com.project.libgen.presentation.components.DisplayName
import com.project.libgen.presentation.components.Email
import com.project.libgen.presentation.components.Password
import com.project.libgen.presentation.components.util.SnackbarController
import com.project.libgen.presentation.components.util.UserState
import kotlinx.coroutines.launch

@Composable
fun UserSignUpScreen(navController: NavController) {
    ScreenContent(navController)
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ScreenContent(
    navController: NavController,
    viewModel: UserSignUpViewModel = hiltViewModel()
) {
    val state: UserState by viewModel.signupState.observeAsState(UserState())
    val scaffoldState = rememberScaffoldState()
    val snackbarController = SnackbarController(viewModel.viewModelScope)
    LaunchedEffect(key1 = state, block = {
        state.user?.let {
            navController.popBackStack()
            navController.navigate(Screen.BookList.route)
        }
    })
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Register",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DisplayName(viewModel.displayNameState)
                Email(viewModel.emailState)
                Password(viewModel.passwordState)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier.padding(end = 10.dp),
                        onClick = {
                            viewModel.displayNameState.clear()
                            viewModel.emailState.clear()
                            viewModel.passwordState.clear()
                        }
                    ) {
                        Text(text = "CLEAR")
                    }
                    Button(
                        onClick = viewModel::signUp,
                        enabled = viewModel.displayNameState.text.isNotBlank() && viewModel.emailState.isValid() && viewModel.passwordState.isValid()
                    ) {
                        Text(text = "SIGNUP")
                    }
                }
            }
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            if (state.error.isNotBlank()) {
                snackbarController.getScope().launch {
                    snackbarController.showSnackbar(
                        scaffoldState = scaffoldState,
                        state.error
                    )
                }
            }
        }
    )
}