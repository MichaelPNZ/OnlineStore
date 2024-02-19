package com.example.onlinestore.data

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
    private val itemsDao = db.itemsDao()

    private val recipeApiService: ItemsApiService by lazy {
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
}