package com.example.onlinestore.ui.profileScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlinestore.data.ItemsRepository
import com.example.onlinestore.model.Item
import com.example.onlinestore.model.User
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val appContext = application
    private val itemsRepository = ItemsRepository(appContext)
    private val _profileUIState = MutableLiveData<ProfileUIState>()
    val profileUIState: LiveData<ProfileUIState>
        get() = _profileUIState

    fun loadUser() {
        viewModelScope.launch {
            val userCache = itemsRepository.getUserFromCache()
            val itemsCache = itemsRepository.getItemsListFromCache()
            _profileUIState.postValue(ProfileUIState(
                user = userCache,
                items = itemsCache
            ))
        }
    }

    fun deletedCache() {
        viewModelScope.launch {
            itemsRepository.deleteUserFromCache()
            itemsRepository.deleteItemFromCache()
        }
    }

    data class ProfileUIState (
        var user: User? = null,
        var items: List<Item>? = emptyList(),
    )
}