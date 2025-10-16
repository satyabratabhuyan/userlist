package com.satya.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satya.core.utils.Resource
import com.satya.domain.model.User
import com.satya.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserState(val users: List<User> = emptyList(), val isLoading: Boolean = false, val error: String? = null)

@HiltViewModel
class UserViewModel @Inject constructor(private val repo: UserRepository): ViewModel() {

    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state

    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            repo.getUsers().collect { result ->
                when (result) {
                    is Resource.Loading -> _state.value = UserState(isLoading = true)
                    is Resource.Success -> _state.value = UserState(users = result.data ?: emptyList())
                    is Resource.Error -> _state.value = UserState(error = result.message)
                }
            }
        }
    }
}
