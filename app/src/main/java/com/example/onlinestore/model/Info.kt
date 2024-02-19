package com.example.onlinestore.model

import kotlinx.serialization.Serializable

@Serializable
data class Info(
    val title: String,
    val value: String,
)
