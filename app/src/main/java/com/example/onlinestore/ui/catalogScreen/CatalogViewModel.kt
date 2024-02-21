package com.example.onlinestore.ui.catalogScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlinestore.data.ItemsRepository
import com.example.onlinestore.model.Item
import kotlinx.coroutines.launch

class CatalogViewModel(application: Application) : AndroidViewModel(application) {

    private val appContext = application
    private val itemsRepository = ItemsRepository(appContext)
    private val _catalogUIState = MutableLiveData<CatalogUIState>()
    val catalogUIState: LiveData<CatalogUIState>
        get() = _catalogUIState

    fun loadCatalog() {
        viewModelScope.launch {
            val itemsListCache = itemsRepository.getItemsListFromCache()
            updateUIState(itemsListCache)

            val itemsList = itemsRepository.getItems()?.items

            itemsList?.let { item ->
                val updateItemsList = item.map {
                    it.copy(
                        isFavorite = itemsListCache.any { cacheItem ->
                            cacheItem.id == it.id && cacheItem.isFavorite
                        }
                    )
                }
                updateUIState(updateItemsList)
                itemsRepository.insertItemListIntoCache(updateItemsList)
            }
        }
    }

    fun onFavoritesClicked(productID: String) {
        val currentCatalogUIState = _catalogUIState.value ?: return

        viewModelScope.launch {
            val updatedItemsList = currentCatalogUIState.itemsList.map { item ->
                if (item.id == productID) {
                    item.copy(isFavorite = !item.isFavorite)
                } else {
                    item
                }
            }
            updateUIState(updatedItemsList)
            itemsRepository.insertItemListIntoCache(updatedItemsList)
        }
    }

    private fun updateUIState(itemsList: List<Item>) {
        _catalogUIState.postValue(
            CatalogUIState(
                itemsList = itemsList
            )
        )
    }

    data class CatalogUIState(
        var itemsList: List<Item> = emptyList(),
    )
}