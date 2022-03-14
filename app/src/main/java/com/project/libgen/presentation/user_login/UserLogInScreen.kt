package com.project.libgen.presentation.user_login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.project.libgen.R
import com.project.libgen.Screen
import com.project.libgen.presentation.components.Email
import com.project.libgen.presentation.components.Password
import com.project.libgen.presentation.components.util.SnackbarController
import com.project.libgen.presentation.components.util.UserState
import kotlinx.coroutines.launch

@Composable
fun UserLoginScreen(navController: NavController) {
    ScreenContent(navController)
}

@SuppressLint("ResourceType", "CoroutineCreationDuringComposition")
@Composable
private fun ScreenContent(
    navController: NavController,
    viewModel: UserLogInViewModel = hiltViewModel()
) {
    val state by viewModel.loginState.observeAsState(UserState())
    val scaffoldState = rememberScaffoldState()
    val snackbarController = SnackbarController(viewModel.viewModelScope)
    var showDialog by remember { mutableStateOf(false) }
    val signInIntent = viewModel.googleSignInClient.signInIntent
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            val data: Intent? = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                viewModel.googleSignIn(account.idToken!!)
            } catch (e: ApiException) {
                snackbarController.getScope().launch {
                    snackbarController.showSnackbar(
                        scaffoldState = scaffoldState,
                        e.localizedMessage ?: "Something went wrong. Please try again later."
                    )
                }
            }
        })
    LaunchedEffect(key1 = state, block = {
        state.user?.let {
            navController.popBackStack()
            navController.navigate(Screen.BookList.route)
        }
    })
    if (showDialog) {
        AlertDialog(
            title = { Text("Logging in as Guest.") },
            text = { Text("Guest users bookmarks are lost on uninstall") },
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    viewModel.anonLogIn()
                }) {
                    Text("OK")
                }
            })
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )
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
                TextButton(onClick = {
                    launcher.launch(signInIntent)
                }) {
                    Row(
                        Modifier.padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_google_icon),
                            modifier = Modifier.padding(end = 5.dp),
                            contentDescription = null
                        )
                        Text(text = "Sign in with Google")
                    }
                }
                Email(viewModel.emailState)
                Password(viewModel.passwordState)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { showDialog = true }
                    ) {
                        Text(text = "Guest")
                    }
                    Button(
                        onClick = {
                            viewModel.emailState.clear()
                            viewModel.passwordState.clear()
                        }
                    ) {
                        Text(text = "Clear")
                    }
                    Button(
                        onClick = {
                            navController.navigate(route = Screen.UserSignUp.route)
                        }
                    ) {
                        Text(text = "Register?")
                    }
                    Button(
                        enabled = viewModel.emailState.isValid() && viewModel.passwordState.isValid(),
                        onClick = viewModel::logIn
                    ) {
                        Text(text = "Login")
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