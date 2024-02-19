package com.example.loginscreen.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    @ColumnInfo(name = "firstNAme")val firstNAme: String,
    @ColumnInfo(name = "lastName")val lastName: String,
    @ColumnInfo(name = "phoneNumber")val phoneNumber: String,
)