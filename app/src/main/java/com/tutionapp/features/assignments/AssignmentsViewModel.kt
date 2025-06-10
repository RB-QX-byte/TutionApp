package com.tutionapp.features.assignments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutionapp.data.model.Assignment
import com.tutionapp.data.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssignmentsViewModel @Inject constructor(
    private val repository: FirestoreRepository
) : ViewModel() {
    
    private val _assignments = MutableStateFlow<List<Assignment>>(emptyList())
    val assignments: StateFlow<List<Assignment>> = _assignments.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    init {
        loadAssignments()
    }
    
    private fun loadAssignments() {
        viewModelScope.launch {
            repository.getAssignments().collect { assignmentList ->
                _assignments.value = assignmentList.sortedBy { it.dueDate }
            }
        }
    }
    
    fun addAssignment(title: String, subject: String, dueDate: Long, details: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val assignment = Assignment(
                    title = title,
                    subject = subject,
                    dueDate = dueDate,
                    details = details
                )
                repository.addAssignment(assignment)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun deleteAssignment(assignment: Assignment) {
        viewModelScope.launch {
            try {
                repository.deleteAssignment(assignment.id)
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
