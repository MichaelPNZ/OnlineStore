package com.example.onlinestore.data

import com.example.onlinestore.model.Item
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ItemsApiService {

    @GET("items/{id}")
    fun getItemById(@Path("id") item: String?): Call<Item>

    @GET("items")
    fun getItemsByIds(@Query("ids") sort: String?): Call<List<Item>>

    @GET("items")
    fun getItems(): Call<List<Item>>

}