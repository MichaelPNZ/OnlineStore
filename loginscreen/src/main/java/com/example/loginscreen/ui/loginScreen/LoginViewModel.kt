package com.example.loginscreen.ui.loginScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginscreen.data.UserRepository
import com.example.loginscreen.model.User
import kotlinx.coroutines.launch

class LoginViewModel(application: Application): AndroidViewModel(application)  {

    private val itemsRepository = UserRepository(application)

    fun addUser(firstName: String, lastName: String, phoneNumber: String) {
        val user = User(
            firstName,
            lastName,
            phoneNumber
        )
        viewModelScope.launch {
            itemsRepository.insertUserIntoCache(user)
        }
    }
}