package com.tutionapp.features.tests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutionapp.data.model.Test
import com.tutionapp.data.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestsViewModel @Inject constructor(
    private val repository: FirestoreRepository
) : ViewModel() {
    
    private val _tests = MutableStateFlow<List<Test>>(emptyList())
    val tests: StateFlow<List<Test>> = _tests.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    init {
        loadTests()
    }
    
    private fun loadTests() {
        viewModelScope.launch {
            repository.getTests().collect { testList ->
                _tests.value = testList.sortedBy { it.testDate }
            }
        }
    }
    
    fun addTest(title: String, subject: String, testDate: Long, maxMarks: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val test = Test(
                    title = title,
                    subject = subject,
                    testDate = testDate,
                    maxMarks = maxMarks
                )
                repository.addTest(test)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun deleteTest(test: Test) {
        viewModelScope.launch {
            try {
                repository.deleteTest(test.id)
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
