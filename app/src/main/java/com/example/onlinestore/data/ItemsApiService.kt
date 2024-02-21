package com.example.onlinestore.data

import com.example.onlinestore.model.Items
import retrofit2.Call
import retrofit2.http.GET

interface ItemsApiService {

    @GET("items")
    fun getItems(): Call<Items>

}