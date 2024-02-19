package com.example.login.data

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(context: Context) {

    private val db: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "database-user"
    )
        .fallbackToDestructiveMigration()
        .build()

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val userDao = db.userDao()

    suspend fun getUserFromCache(): com.example.loginscreen.model.User? {
        return withContext(ioDispatcher) {
            userDao.getUser()
        }
    }

    suspend fun insertUserIntoCache(user: com.example.loginscreen.model.User) {
        withContext(ioDispatcher) {
            userDao.insert(user)
        }
    }

    suspend fun deleteUserFromCache() {
        withContext(ioDispatcher) {
            userDao.delete()
        }
    }
}