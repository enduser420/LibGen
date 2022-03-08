package com.project.libgen.presentation.splash_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.libgen.presentation.components.util.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private var _currentUser = MutableLiveData(UserState(user = Firebase.auth.currentUser))
    val currentUser: LiveData<UserState>
        get() = _currentUser

    init {
        Firebase.auth.currentUser?.let {
            _currentUser.postValue(UserState(user = it))
        }
    }
}