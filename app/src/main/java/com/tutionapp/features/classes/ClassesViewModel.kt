package com.tutionapp.features.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutionapp.data.model.TutionClass
import com.tutionapp.data.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassesViewModel @Inject constructor(
    private val repository: FirestoreRepository
) : ViewModel() {
    
    private val _classes = MutableStateFlow<List<TutionClass>>(emptyList())
    val classes: StateFlow<List<TutionClass>> = _classes.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    init {
        loadClasses()
    }
    
    private fun loadClasses() {
        viewModelScope.launch {
            repository.getClasses().collect { classList ->
                _classes.value = classList
            }
        }
    }
    
    fun addClass(subject: String, teacherName: String, schedule: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val tutionClass = TutionClass(
                    subject = subject,
                    teacherName = teacherName,
                    schedule = schedule
                )
                repository.addClass(tutionClass)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun deleteClass(tutionClass: TutionClass) {
        viewModelScope.launch {
            try {
                repository.deleteClass(tutionClass.id)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
    
    fun clearError() {
        _error.value = null
    }
}
