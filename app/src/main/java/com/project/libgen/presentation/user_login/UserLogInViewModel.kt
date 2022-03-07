package com.project.libgen.presentation.user_login

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.libgen.R
import com.project.libgen.Screen
import com.project.libgen.core.util.Resource
import com.project.libgen.presentation.components.EmailState
import com.project.libgen.presentation.components.PasswordState
import com.project.libgen.presentation.components.util.UserState
import com.project.libgen.use_case.firebase.GoogleSignInUseCase
import com.project.libgen.use_case.firebase.LogInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserLogInViewModel @Inject constructor(
    application: Application,
    private val LogInUseCase: LogInUseCase,
    private val GoogleSignInUseCase: GoogleSignInUseCase,
) : ViewModel() {

    val emailState = EmailState()
    val passwordState = PasswordState()

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(application.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(application.baseContext, gso)

    private var _loginState: MutableLiveData<UserState> = MutableLiveData(UserState())
    val loginState: LiveData<UserState>
        get() = _loginState

    private val _startDestination = mutableStateOf(Screen.UserLogin.route)
    val startDestination = _startDestination

    init {
        emailState.text = "helloworld@gmail.com"
        passwordState.text = "helloworld@123"
        Firebase.auth.currentUser?.let {
            _loginState.postValue(UserState(user = it))
        }
    }

    fun logIn() {
        LogInUseCase(
            emailState.text.trim(),
            passwordState.text.trim()
        ).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _loginState.postValue(UserState(isLoading = true))
                }
                is Resource.Success -> {
                    _loginState.postValue(UserState(user = result.data))
                    _startDestination.value = Screen.BookList.route
                }
                is Resource.Error -> {
                    _loginState.postValue(result.message?.let {
                        UserState(error = it)
                    })
                }
            }
        }.launchIn(viewModelScope)
    }

    fun anonLogIn() {
        LogInUseCase()
            .onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _loginState.postValue(UserState(isLoading = true))
                }
                is Resource.Success -> {
                    _loginState.postValue(UserState(user = result.data))
                    _startDestination.value = Screen.BookList.route
                }
                is Resource.Error -> {
                    _loginState.postValue(result.message?.let {
                        UserState(error = it)
                    })
                }
            }
        }.launchIn(viewModelScope)
    }

    fun googleSignIn(userId: String) {
        val credential = GoogleAuthProvider.getCredential(userId, null)
        GoogleSignInUseCase(
            credential
        ).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _loginState.postValue(UserState(isLoading = true))
                }
                is Resource.Success -> {
                    _loginState.postValue(UserState(user = result.data))
                    _startDestination.value = Screen.BookList.route
                }
                is Resource.Error -> {
                    _loginState.postValue(result.message?.let {
                        UserState(error = it)
                    })
                }
            }
        }.launchIn(viewModelScope)
    }
}