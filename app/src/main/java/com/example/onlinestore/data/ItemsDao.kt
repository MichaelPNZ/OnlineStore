package com.example.onlinestore.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.onlinestore.model.Item

@Dao
interface ItemsDao {

    @Query("SELECT * FROM item")
    fun getItemsList(): List<Item>

    @Query("SELECT * FROM item WHERE id = :itemId")
    fun getItemById(itemId: String): Item

    @Query("SELECT * FROM item WHERE isFavorite = 1")
    fun getItemsByFavorite(): List<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItemList(items: List<Item>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: Item)

    @Query("DELETE FROM Item")
    fun delete()

}