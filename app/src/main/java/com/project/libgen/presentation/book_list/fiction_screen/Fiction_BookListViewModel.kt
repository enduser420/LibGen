package com.project.libgen.presentation.book_list.fiction_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.libgen.presentation.book_list.BookListState
import com.project.libgen.presentation.components.util.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class Fiction_BookListViewModel @Inject constructor() : ViewModel() {

    private var _currentUser: MutableLiveData<UserState> =
        MutableLiveData(UserState(user = Firebase.auth.currentUser))
    val currentUser: LiveData<UserState>
        get() = _currentUser

    private var _bookList = mutableStateOf(BookListState())
    val bookList: State<BookListState>
        get() = _bookList

    fun logout() {
        _currentUser.postValue(UserState(user = null))
        Firebase.auth.signOut()
    }
}