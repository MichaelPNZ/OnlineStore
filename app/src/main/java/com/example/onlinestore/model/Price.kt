package com.example.onlinestore.model

import kotlinx.serialization.Serializable

@Serializable
data class Price(
    val price: String,
    val discount: Int,
    val priceWithDiscount: String,
    val unit: String,
)