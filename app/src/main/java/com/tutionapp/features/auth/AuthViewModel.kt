package com.tutionapp.features.auth

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
class AuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val repository: FirestoreRepository
) : ViewModel() {
    
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()
    
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()
    
    init {
        checkAuthState()
    }
    
    private fun checkAuthState() {
        _isLoggedIn.value = firebaseAuth.currentUser != null
    }
    
    fun login(email: String, password: String) {
        if (!isValidEmail(email)) {
            _authState.value = AuthState.Error("Invalid email format")
            return
        }
        
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _authState.value = AuthState.Success
                        _isLoggedIn.value = true
                    } else {
                        _authState.value = AuthState.Error(task.exception?.message ?: "Login failed")
                    }
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Login failed")
            }
        }
    }
    
    fun register(email: String, password: String, name: String) {
        if (!isValidEmail(email)) {
            _authState.value = AuthState.Error("Invalid email format")
            return
        }
        
        if (!isValidPassword(password)) {
            _authState.value = AuthState.Error("Password must be at least 8 characters")
            return
        }
        
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        user?.let {
                            val userData = User(uid = it.uid, email = email, name = name)
                            viewModelScope.launch {
                                try {
                                    repository.saveUser(userData)
                                    _authState.value = AuthState.Success
                                    _isLoggedIn.value = true
                                } catch (e: Exception) {
                                    _authState.value = AuthState.Error("Failed to save user data")
                                }
                            }
                        }
                    } else {
                        _authState.value = AuthState.Error(task.exception?.message ?: "Registration failed")
                    }
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Registration failed")
            }
        }
    }
    
    fun logout() {
        firebaseAuth.signOut()
        _isLoggedIn.value = false
        _authState.value = AuthState.Idle
    }
    
    fun resetAuthState() {
        _authState.value = AuthState.Idle
    }
    
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    
    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }
}
