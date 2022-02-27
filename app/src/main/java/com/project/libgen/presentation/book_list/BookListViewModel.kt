package com.project.libgen.presentation.book_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.libgen.core.util.Resource
import com.project.libgen.presentation.components.util.UserState
import com.project.libgen.repository.AuthRepository
import com.project.libgen.use_case.get_book_list.GetBookListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase,
    private val repository: AuthRepository
) : ViewModel() {

    private var _currentUser: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    private var _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private var _bookList = mutableStateOf(BookListState())
    val bookList: State<BookListState>
        get() = _bookList

    val filterText =
        listOf("Title", "Author(s)", "Series", "Publisher", "Year", "Language", "Tags", "Extension")
    private val filterOptions =
        listOf("title", "author", "series", "publisher", "year", "language", "tags", "extension")
    val filterIndex = mutableStateOf(0)

    fun onSearchQueryChanged(newSearchQuery: String) {
        _searchQuery.value = newSearchQuery
    }

    val searched = mutableStateOf(false)

    init {
        _searchQuery.value = "algorithm"
//        getcurrentUser().onEach { result ->
//            when (result) {
//                is Resource.Success -> {
//                    _currentUser.postValue(UserState(user = result.data))
//                }
//                is Resource.Error -> {
//                    _currentUser.postValue(result.message?.let {
//                        UserState(error = it)
//                    })
//                }
//                is Resource.Loading -> {
//                    _currentUser.postValue(UserState(isLoading = true))
//                }
//            }
//        }.launchIn(CoroutineScope(IO))

//        _currentUser.postValue(
//            Firebase.auth.currentUser
//        )
    }

    fun onSearch() {
        getBookListUseCase(
            _searchQuery.value,
            filterOptions[filterIndex.value]
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    searched.value = true
                    _bookList.value = BookListState(bookList = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _bookList.value = BookListState(
                        error = result.message ?: "Something went wrong."
                    )
                }
                is Resource.Loading -> {
                    _bookList.value = BookListState(isLoading = true)
                }
            }
        }
            .launchIn(CoroutineScope(IO)) // NOTE: Don't use .launchIn(viewModelScope), since this function is not called from the init{} block but from the BookList Screen
    }

//    private fun getcurrentUser() = channelFlow {
//        try {
//            send(Resource.Loading())
//            val currentUser = Firebase.auth.currentUser
//            send(Resource.Success(currentUser))
//        } catch (e: Exception) {
//            send(Resource.Error(message = e.localizedMessage ?: "Something went wrong"))
//        }
//    }

//    fun logout() = channelFlow {
//        try {
//            send(Resource.Loading())
//            send(Resource.Success(null))
//        } catch (e: Exception) {
//            send(Resource.Error(message = e.localizedMessage ?: ""))
//        } as Unit
//    }.onEach { result ->
//        when (result) {
//            is Resource.Success -> {
//                _currentUser.postValue(UserState(user = result.data))
//                Firebase.auth.signOut()
//            }
//            is Resource.Error -> {
//                _currentUser.postValue(result.message?.let {
//                    UserState(error = it)
//                })
//            }
//            is Resource.Loading -> {
//                _currentUser.postValue(UserState(isLoading = true))
//            }
//        }
//    }.launchIn(CoroutineScope(IO))


    fun logout() {
        _currentUser.postValue(null)
        Firebase.auth.signOut()
    }
}