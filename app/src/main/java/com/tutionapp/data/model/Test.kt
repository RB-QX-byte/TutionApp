package com.tutionapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Test(
    val id: String = "",
    val title: String = "",
    val subject: String = "",
    val testDate: Long = 0L,
    val maxMarks: Int = 0
)
