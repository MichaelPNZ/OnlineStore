package com.example.onlinestore.model

import kotlinx.serialization.Serializable

@Serializable
data class Feedback(
    val count: Int,
    val rating: Double,
)
