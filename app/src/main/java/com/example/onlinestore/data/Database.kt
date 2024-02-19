package com.example.onlinestore.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.onlinestore.model.Converters
import com.example.onlinestore.model.Item

@Database(entities = [ Item::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemsDao(): ItemsDao

}