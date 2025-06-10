package com.tutionapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TutionClass(
    val id: String = "",
    val subject: String = "",
    val teacherName: String = "",
    val schedule: String = ""
)
