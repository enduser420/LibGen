package com.project.libgen.presentation.user_settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.project.libgen.presentation.components.DisplayNameState
import com.project.libgen.presentation.components.EmailState
import com.project.libgen.presentation.components.PasswordState
import com.project.libgen.presentation.components.util.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserSettingsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val displayNameState = DisplayNameState()
    val emailState = EmailState()
    val passwordState = PasswordState()
    val newPasswordState = PasswordState()

    private var _userState: MutableLiveData<UserState> = MutableLiveData(UserState())
    val userState: LiveData<UserState>
        get() = _userState

    private var _navBack: MutableLiveData<Boolean> = MutableLiveData(false)
    val navBack: LiveData<Boolean>
        get() = _navBack

    private val _mode = mutableStateOf("")
    val mode
        get() = _mode


    init {
        Firebase.auth.currentUser?.let {
            _userState.postValue(UserState(user = it))
        }

        savedStateHandle.get<String>("mode")?.let { mode ->
            _mode.value = mode
        }
    }

    fun changeEmail() {
        _userState.value!!.user?.let { user ->
            val creds = EmailAuthProvider.getCredential(user.email.toString(), passwordState.text)
            user.reauthenticate(creds).addOnSuccessListener {
                _userState.postValue(UserState(isLoading = true))
                user.updateEmail(emailState.text).addOnCompleteListener {
                    if (it.isSuccessful)
                        _navBack.postValue(true)
                }.addOnFailureListener {
                    _userState.postValue(UserState(error = "Could not update profile. Please try again later."))
                }
            }.addOnFailureListener {
                _userState.postValue(UserState(error = "Wrong Password entered."))
            }

//            emailChangeUseCase(user, emailState.text, passwordState.text)
//                .onEach { result ->
//                    when (result) {
//                        is Resource.Loading -> {
//                            _userState.postValue(UserState(isLoading = true))
//                        }
//                        is Resource.Error -> {
//                            _userState.postValue(UserState(user = result.data))
//                        }
//                        is Resource.Success -> {
//                            _userState.postValue(UserState(error = result.message ?: ""))
//                        }
//                    }
//                }.launchIn(CoroutineScope(IO))
        }
    }

    fun changePassword() {
        _userState.value?.user?.let { user ->
            val creds = EmailAuthProvider.getCredential(user.email.toString(), passwordState.text)
            user.reauthenticate(creds).addOnSuccessListener {
                _userState.postValue(UserState(isLoading = true))
                user.updatePassword(newPasswordState.text).addOnSuccessListener {
                    _navBack.postValue(true)
                }.addOnFailureListener {
                    _userState.postValue(UserState(error = "Something went wrong."))
                }
            }.addOnFailureListener {
                _userState.postValue(UserState(error = "Something went wrong."))
            }
        }
    }

    fun changeDisplayName() {
        _userState.value?.user?.let { user ->
            val creds = EmailAuthProvider.getCredential(user.email.toString(), passwordState.text)
            user.reauthenticate(creds).addOnSuccessListener {
                _userState.postValue(UserState(isLoading = true))
                user.updateProfile(
                    userProfileChangeRequest {
                        displayName = displayNameState.text
                    }
                ).addOnSuccessListener {
                    _navBack.postValue(true)
                }.addOnFailureListener {
                    _userState.postValue(UserState(error = "Something went wrong."))
                }
            }.addOnFailureListener {
                _userState.postValue(UserState(error = "Something went wrong."))
            }
        }
    }
}