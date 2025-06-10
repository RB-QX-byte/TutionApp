package com.tutionapp.features.students

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutionapp.data.model.Student
import com.tutionapp.data.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(
    private val repository: FirestoreRepository
) : ViewModel() {
    
    private val _students = MutableStateFlow<List<Student>>(emptyList())
    val students: StateFlow<List<Student>> = _students.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    init {
        loadStudents()
    }
    
    private fun loadStudents() {
        viewModelScope.launch {
            repository.getStudents().collect { studentList ->
                _students.value = studentList
            }
        }
    }
    
    fun addStudent(name: String, grade: String, contactInfo: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val student = Student(
                    name = name,
                    grade = grade,
                    contactInfo = contactInfo
                )
                repository.addStudent(student)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun deleteStudent(student: Student) {
        viewModelScope.launch {
            try {
                repository.deleteStudent(student.id)
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
