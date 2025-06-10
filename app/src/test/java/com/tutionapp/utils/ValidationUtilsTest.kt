package com.tutionapp.utils

import org.junit.Test
import org.junit.Assert.*

class ValidationUtilsTest {
    
    @Test
    fun `isValidEmail returns true for valid emails`() {
        assertTrue(ValidationUtils.isValidEmail("test@example.com"))
        assertTrue(ValidationUtils.isValidEmail("user.name@domain.co.uk"))
        assertTrue(ValidationUtils.isValidEmail("user+tag@example.org"))
    }
    
    @Test
    fun `isValidEmail returns false for invalid emails`() {
        assertFalse(ValidationUtils.isValidEmail(""))
        assertFalse(ValidationUtils.isValidEmail("invalid-email"))
        assertFalse(ValidationUtils.isValidEmail("@example.com"))
        assertFalse(ValidationUtils.isValidEmail("user@"))
        assertFalse(ValidationUtils.isValidEmail("user@.com"))
    }
    
    @Test
    fun `isValidPassword returns true for valid passwords`() {
        assertTrue(ValidationUtils.isValidPassword("123456"))
        assertTrue(ValidationUtils.isValidPassword("password123"))
        assertTrue(ValidationUtils.isValidPassword("StrongP@ssw0rd"))
    }
    
    @Test
    fun `isValidPassword returns false for invalid passwords`() {
        assertFalse(ValidationUtils.isValidPassword(""))
        assertFalse(ValidationUtils.isValidPassword("12345"))
        assertFalse(ValidationUtils.isValidPassword("abc"))
    }
    
    @Test
    fun `isValidName returns true for valid names`() {
        assertTrue(ValidationUtils.isValidName("John"))
        assertTrue(ValidationUtils.isValidName("John Doe"))
        assertTrue(ValidationUtils.isValidName("María García"))
    }
    
    @Test
    fun `isValidName returns false for invalid names`() {
        assertFalse(ValidationUtils.isValidName(""))
        assertFalse(ValidationUtils.isValidName(" "))
        assertFalse(ValidationUtils.isValidName("A"))
    }
    
    @Test
    fun `isValidGrade returns true for valid grades`() {
        assertTrue(ValidationUtils.isValidGrade("1"))
        assertTrue(ValidationUtils.isValidGrade("12"))
        assertTrue(ValidationUtils.isValidGrade("KG"))
        assertTrue(ValidationUtils.isValidGrade("UKG"))
        assertTrue(ValidationUtils.isValidGrade("Nursery"))
    }
    
    @Test
    fun `isValidGrade returns false for invalid grades`() {
        assertFalse(ValidationUtils.isValidGrade(""))
        assertFalse(ValidationUtils.isValidGrade("0"))
        assertFalse(ValidationUtils.isValidGrade("13"))
        assertFalse(ValidationUtils.isValidGrade("invalid"))
    }
    
    @Test
    fun `isValidContact returns true for valid contacts`() {
        assertTrue(ValidationUtils.isValidContact("1234567890"))
        assertTrue(ValidationUtils.isValidContact("+1234567890"))
        assertTrue(ValidationUtils.isValidContact("123-456-7890"))
        assertTrue(ValidationUtils.isValidContact("123 456 7890"))
    }
    
    @Test
    fun `isValidContact returns false for invalid contacts`() {
        assertFalse(ValidationUtils.isValidContact(""))
        assertFalse(ValidationUtils.isValidContact("123"))
        assertFalse(ValidationUtils.isValidContact("abcdefghij"))
        assertFalse(ValidationUtils.isValidContact("12345678901234567890"))
    }
}
