package com.example.loginscreen.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.loginscreen.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUser(): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("DELETE FROM user")
    fun delete()

}