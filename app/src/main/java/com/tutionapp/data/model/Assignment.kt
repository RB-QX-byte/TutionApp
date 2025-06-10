package com.tutionapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Assignment(
    val id: String = "",
    val title: String = "",
    val subject: String = "",
    val dueDate: Long = 0L,
    val details: String = ""
)
