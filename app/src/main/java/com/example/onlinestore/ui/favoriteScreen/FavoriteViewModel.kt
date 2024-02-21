package com.example.onlinestore.ui.favoriteScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlinestore.data.ItemsRepository
import com.example.onlinestore.model.Item
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val appContext = application
    private val itemsRepository = ItemsRepository(appContext)
    private val _favoriteUIState = MutableLiveData<FavoriteUIState>()
    val favoriteUIState: LiveData<FavoriteUIState>
        get() = _favoriteUIState

    fun loadFavorite() {
        viewModelScope.launch {
            val itemsListCache = itemsRepository.getItemsByFavorite()
            updateUIState(itemsListCache)
        }
    }

    fun onFavoritesClicked(productID: String) {
        val currentCatalogUIState = _favoriteUIState.value ?: return

        viewModelScope.launch {
            val item = currentCatalogUIState.itemsList?.find { it.id == productID} ?: return@launch
            val updatedItemsList = currentCatalogUIState.itemsList?.minus(item) ?: return@launch
            updateUIState(updatedItemsList)
            val updatedItemsListCache = currentCatalogUIState.itemsList?.map { item ->
                if (item.id == productID) {
                    item.copy(isFavorite = !item.isFavorite)
                } else {
                    item
                }
            }
            if (updatedItemsListCache != null) {
                itemsRepository.insertItemListIntoCache(updatedItemsListCache)
            }
        }
    }

    private fun updateUIState(itemsList: List<Item>) {
        _favoriteUIState.postValue(
            FavoriteUIState(
                itemsList = itemsList
            )
        )
    }

    data class FavoriteUIState(
        var itemsList: List<Item>? = emptyList(),
    )
}