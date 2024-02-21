package com.example.onlinestore.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.onlinestore.model.Item
import com.example.onlinestore.model.Items
import com.example.onlinestore.model.User
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class ItemsRepository(context: Context) {

    private val db: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "database-items"
    )
        .fallbackToDestructiveMigration()
        .build()

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val userDao = db.userDao()
    private val itemsDao = db.itemsDao()

    private val itemsApiService: ItemsApiService by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .client(client)
            .build()

        retrofit.create(ItemsApiService::class.java)
    }

    suspend fun getItemsByFavorite(): List<Item> {
        return withContext(ioDispatcher) {
            itemsDao.getItemsByFavorite()
        }
    }

    suspend fun getItemByIdFromCache(itemId: String): Item {
        return withContext(ioDispatcher) {
            itemsDao.getItemById(itemId)
        }
    }

    suspend fun getItemsListFromCache(): List<Item> {
        return withContext(ioDispatcher) {
            itemsDao.getItemsList()
        }
    }

    suspend fun insertItemIntoCache(item: Item) {
        return withContext(ioDispatcher) {
            itemsDao.insertItem(item)
        }
    }

    suspend fun insertItemListIntoCache(items: List<Item>) {
        withContext(ioDispatcher) {
            itemsDao.insertItemList(items)
        }
    }

    suspend fun deleteItemFromCache() {
        withContext(ioDispatcher) {
            itemsDao.delete()
        }
    }

    suspend fun getUserFromCache(): User? {
        return withContext(ioDispatcher) {
            userDao.getUser()
        }
    }

    suspend fun insertUserIntoCache(user: User) {
        withContext(ioDispatcher) {
            userDao.insert(user)
        }
    }

    suspend fun deleteUserFromCache() {
        withContext(ioDispatcher) {
            userDao.delete()
        }
    }

    suspend fun getItems(): Items? {
        return  withContext(ioDispatcher) {
            try {
                val items = itemsApiService.getItems().execute().body()
                items
            } catch (e: Exception) {
                Log.e("!!!", "Error fetching categories", e)
                null
            }
        }
    }
}