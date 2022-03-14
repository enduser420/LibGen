package com.project.libgen.presentation.user_settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
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
import com.project.libgen.presentation.components.util.SettingMode
import com.project.libgen.presentation.components.util.SnackbarController
import com.project.libgen.presentation.components.util.UserState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun UserSettingsScreen(navController: NavController) {
    ScreenContent(navController)
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun ScreenContent(
    navController: NavController,
    viewModel: UserSettingsViewModel = hiltViewModel()
) {
    val state by viewModel.userState.observeAsState(UserState())
    val scaffoldState = rememberScaffoldState()
    val snackbarController = SnackbarController(viewModel.viewModelScope)
    val navBack by viewModel.navBack.observeAsState(false)
    val mode by remember {
        viewModel.mode
    }
    if (navBack) {
        snackbarController.getScope().launch {
            snackbarController.showSnackbar(
                scaffoldState = scaffoldState,
                message = "Profile Updated Successfully"
            )
            delay(500L)
            navController.popBackStack(route = Screen.BookList.route, inclusive = false)
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
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
                when (SettingMode.valueOf(mode)) {
                    SettingMode.CHANGEPASSWORD -> {
                        Password(viewModel.newPasswordState, label = "New Password")
                        Password(viewModel.passwordState)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    viewModel.newPasswordState.clear()
                                    viewModel.passwordState.clear()
                                }
                            ) {
                                Text(text = "Clear")
                            }
                            Button(
                                enabled = viewModel.newPasswordState.isValid() && viewModel.passwordState.isValid(),
                                onClick = viewModel::changePassword
                            ) {
                                Text(text = "Confirm")
                            }
                        }
                    }
                    SettingMode.CHANGEEMAIL -> {
                        Email(viewModel.emailState, "New Email")
                        Password(viewModel.passwordState)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    viewModel.emailState.clear()
                                    viewModel.passwordState.clear()
                                }
                            ) {
                                Text(text = "Clear")
                            }
                            Button(
                                enabled = viewModel.emailState.isValid() && viewModel.passwordState.isValid(),
                                onClick = viewModel::changeEmail
                            ) {
                                Text(text = "Confirm")
                            }
                        }
                    }
                    SettingMode.CHANGEDISPLAYNAME -> {
                        DisplayName(viewModel.displayNameState, "New Display Name")
                        Password(viewModel.passwordState)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    viewModel.displayNameState.clear()
                                    viewModel.passwordState.clear()
                                }
                            ) {
                                Text(text = "Clear")
                            }
                            Button(
                                enabled = viewModel.displayNameState.isValid() && viewModel.passwordState.isValid(),
                                onClick = viewModel::changeDisplayName
                            ) {
                                Text(text = "Confirm")
                            }
                        }
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
