package com.project.libgen.presentation.user_signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.libgen.core.util.Resource
import com.project.libgen.presentation.components.DisplayNameState
import com.project.libgen.presentation.components.EmailState
import com.project.libgen.presentation.components.PasswordState
import com.project.libgen.presentation.components.util.UserState
import com.project.libgen.use_case.firebase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserSignUpViewModel @Inject constructor(
    private val SignUpUseCase: SignUpUseCase
) : ViewModel() {

    val displayNameState = DisplayNameState()
    val emailState = EmailState()
    val passwordState = PasswordState()

    private var _signupState: MutableLiveData<UserState> = MutableLiveData(UserState())
    val signupState: LiveData<UserState>
        get() = _signupState

//    init {
//        displayNameState.text = "testing"
//        emailState.text = "testin@gmail.com"
//        passwordState.text = "testing@123"
//    }

    fun signUp() {
        SignUpUseCase(
            emailState.text.trim(),
            passwordState.text.trim()
        ).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _signupState.postValue(UserState(isLoading = true))
                }
                is Resource.Success -> {
                    _signupState.postValue(UserState(user = result.data))
                }
                is Resource.Error -> {
                    _signupState.postValue(result.message?.let {
                        UserState(error = it)
                    })
                }
            }
        }.launchIn(viewModelScope)
    }
}