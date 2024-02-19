package com.example.login.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.loginscreen.model.User

@Database(entities = [ User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}