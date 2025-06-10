package com.tutionapp.demo

/**
 * Demo Configuration for Testing UI Without Firebase
 * 
 * This shows how the app would look and behave with sample data.
 * To enable demo mode:
 * 1. Replace Firebase dependencies with mock data
 * 2. Use local state instead of Firestore
 * 3. Disable authentication for testing
 */

object DemoConfig {
    const val DEMO_MODE = false // Set to true for demo without Firebase
    
    // Sample data for testing UI
    val sampleStudents = listOf(
        mapOf("id" to "1", "name" to "John Doe", "grade" to "10", "contact" to "1234567890"),
        mapOf("id" to "2", "name" to "Jane Smith", "grade" to "9", "contact" to "0987654321"),
        mapOf("id" to "3", "name" to "Mike Johnson", "grade" to "11", "contact" to "5555555555")
    )
    
    val sampleAssignments = listOf(
        mapOf("id" to "1", "title" to "Math Homework", "subject" to "Mathematics", "dueDate" to "15/06/2025", "details" to "Chapter 5 exercises"),
        mapOf("id" to "2", "title" to "Science Project", "subject" to "Physics", "dueDate" to "20/06/2025", "details" to "Build a simple circuit"),
        mapOf("id" to "3", "title" to "Essay Writing", "subject" to "English", "dueDate" to "18/06/2025", "details" to "Write about environmental conservation")
    )
    
    val sampleClasses = listOf(
        mapOf("id" to "1", "subject" to "Mathematics", "teacherName" to "Mr. Anderson", "schedule" to "Mon-Wed-Fri 10:00 AM"),
        mapOf("id" to "2", "subject" to "Physics", "teacherName" to "Dr. Wilson", "schedule" to "Tue-Thu 2:00 PM"),
        mapOf("id" to "3", "subject" to "English", "teacherName" to "Ms. Thompson", "schedule" to "Mon-Wed 4:00 PM")
    )
    
    val sampleTests = listOf(
        mapOf("id" to "1", "title" to "Math Quiz", "subject" to "Mathematics", "date" to "25/06/2025", "maxMarks" to "50"),
        mapOf("id" to "2", "title" to "Physics Exam", "subject" to "Physics", "date" to "30/06/2025", "maxMarks" to "100"),
        mapOf("id" to "3", "title" to "English Test", "subject" to "English", "date" to "28/06/2025", "maxMarks" to "75")
    )
}
