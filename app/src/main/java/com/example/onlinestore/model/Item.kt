package com.example.onlinestore.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
@Entity
data class Item(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "subtitle") val subtitle: String,
    @ColumnInfo(name = "price") val price: Price,
    @ColumnInfo(name = "feedback") val feedback: Feedback,
    @ColumnInfo(name = "tags") val tags: List<String>,
    @ColumnInfo(name = "available") val available: Int,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "info") val info: List<Info>,
    @ColumnInfo(name = "ingredients") val ingredients: String,
    @ColumnInfo(name = "isFavorite") @Transient val isFavorite: Boolean = false,
)

class Converters {
    @TypeConverter
    fun fromInfoList(list: List<Info>?): String {
        return Json.encodeToString(list ?: emptyList())
    }

    @TypeConverter
    fun toInfoList(value: String): List<Info> {
        return try {
            Json.decodeFromString(value)
        } catch (e: Exception) {
            emptyList()
        }
    }

    @TypeConverter
    fun fromTagsList(list: List<String>?): String {
        return Json.encodeToString(list ?: emptyList())
    }

    @TypeConverter
    fun toTagsList(value: String): List<String> {
        return try {
            Json.decodeFromString(value)
        } catch (e: Exception) {
            emptyList()
        }
    }

    @TypeConverter
    fun fromPrice(price: Price): String {
        return Json.encodeToString(price)
    }

    @TypeConverter
    fun toPrice(value: String): Price {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromFeedback(feedback: Feedback): String {
        return Json.encodeToString(feedback)
    }

    @TypeConverter
    fun toFeedback(value: String): Feedback {
        return Json.decodeFromString(value)
    }
}