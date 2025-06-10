package com.tutionapp.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val displayDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    
    fun formatDate(date: Date): String {
        return dateFormat.format(date)
    }
    
    fun formatDateForDisplay(date: Date): String {
        return displayDateFormat.format(date)
    }
    
    fun parseDate(dateString: String): Date? {
        return try {
            dateFormat.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }
    
    fun getCurrentDate(): String {
        return formatDate(Date())
    }
    
    fun getCurrentDateForDisplay(): String {
        return formatDateForDisplay(Date())
    }
    
    fun isDateInFuture(dateString: String): Boolean {
        val date = parseDate(dateString)
        return date?.after(Date()) ?: false
    }
    
    fun isDateInPast(dateString: String): Boolean {
        val date = parseDate(dateString)
        return date?.before(Date()) ?: false
    }
    
    fun getDaysUntilDate(dateString: String): Long? {
        val date = parseDate(dateString)
        return if (date != null) {
            val currentTime = Date().time
            val targetTime = date.time
            val diffInMillis = targetTime - currentTime
            diffInMillis / (1000 * 60 * 60 * 24) // Convert to days
        } else null
    }
}
