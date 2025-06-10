package com.tutionapp.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.tutionapp.data.model.User
import com.tutionapp.data.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: FirestoreRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    init {
        loadUserProfile()
    }
    
    private fun loadUserProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val currentUser = firebaseAuth.currentUser
                currentUser?.let { firebaseUser ->
                    val userData = repository.getUser(firebaseUser.uid)
                    _user.value = userData ?: User(
                        uid = firebaseUser.uid,
                        email = firebaseUser.email ?: "",
                        name = firebaseUser.displayName ?: ""
                    )
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun updateUserName(newName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val currentUser = firebaseAuth.currentUser
                currentUser?.let { firebaseUser ->
                    val updates = mapOf("name" to newName)
                    repository.updateUser(firebaseUser.uid, updates)
                    _user.value = _user.value?.copy(name = newName)
                    _error.value = null
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun clearError() {
        _error.value = null
    }
}
