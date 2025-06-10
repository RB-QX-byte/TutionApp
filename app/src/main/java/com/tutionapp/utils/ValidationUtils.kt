package com.tutionapp.utils

object ValidationUtils {
    
    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$".toRegex()
        return email.isNotBlank() && emailRegex.matches(email)
    }
    
    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
    
    fun isValidName(name: String): Boolean {
        return name.isNotBlank() && name.trim().length >= 2
    }
    
    fun isValidGrade(grade: String): Boolean {
        return grade.isNotBlank() && grade.matches("^[1-9]|1[0-2]|KG|UKG|Nursery$".toRegex())
    }
    
    fun isValidContact(contact: String): Boolean {
        val phoneRegex = "^[+]?[0-9]{10,15}$".toRegex()
        return contact.isNotBlank() && phoneRegex.matches(contact.replace(" ", "").replace("-", ""))
    }
    
    fun getEmailError(email: String): String? {
        return when {
            email.isBlank() -> "Email is required"
            !isValidEmail(email) -> "Please enter a valid email address"
            else -> null
        }
    }
    
    fun getPasswordError(password: String): String? {
        return when {
            password.isBlank() -> "Password is required"
            password.length < 6 -> "Password must be at least 6 characters"
            else -> null
        }
    }
    
    fun getNameError(name: String): String? {
        return when {
            name.isBlank() -> "Name is required"
            name.trim().length < 2 -> "Name must be at least 2 characters"
            else -> null
        }
    }
    
    fun getGradeError(grade: String): String? {
        return when {
            grade.isBlank() -> "Grade is required"
            !isValidGrade(grade) -> "Please enter a valid grade (1-12, KG, UKG, or Nursery)"
            else -> null
        }
    }
    
    fun getContactError(contact: String): String? {
        return when {
            contact.isBlank() -> "Contact is required"
            !isValidContact(contact) -> "Please enter a valid phone number"
            else -> null
        }
    }
}
