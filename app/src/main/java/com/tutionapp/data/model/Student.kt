package com.tutionapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Student(
    val id: String = "",
    val name: String = "",
    val grade: String = "",
    val contactInfo: String = ""
)
