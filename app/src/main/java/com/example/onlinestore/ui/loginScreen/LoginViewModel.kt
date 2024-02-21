package com.example.onlinestore.ui.loginScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinestore.data.ItemsRepository
import kotlinx.coroutines.launch

class LoginViewModel(application: Application): AndroidViewModel(application)  {

    private val itemsRepository = ItemsRepository(application)

    fun addUser(firstName: String, lastName: String, phoneNumber: String) {
        val user = com.example.onlinestore.model.User(
            firstName,
            lastName,
            phoneNumber
        )
        viewModelScope.launch {
            itemsRepository.insertUserIntoCache(user)
        }
    }
}