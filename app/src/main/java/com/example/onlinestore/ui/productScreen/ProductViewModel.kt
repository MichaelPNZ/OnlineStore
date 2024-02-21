package com.example.onlinestore.ui.productScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlinestore.data.ItemsRepository
import com.example.onlinestore.model.Item
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val appContext = application
    private val itemsRepository = ItemsRepository(appContext)
    private val _productUIState = MutableLiveData<ProductUIState>()
    val productUIState: LiveData<ProductUIState>
        get() = _productUIState

    fun loadProduct(itemId: String) {
        viewModelScope.launch {
            val itemCache = itemsRepository.getItemByIdFromCache(itemId)
            _productUIState.postValue(ProductUIState(itemCache))
        }
    }

    fun onFavoritesClicked() {
        val currentProductUIState = _productUIState.value ?: return

        viewModelScope.launch {
            val updatedItem = currentProductUIState.item?.copy(
                isFavorite = !currentProductUIState.item?.isFavorite!!
            )

            _productUIState.postValue(ProductUIState(updatedItem))

            if (updatedItem != null) {
                itemsRepository.insertItemIntoCache(updatedItem)
            }
        }
    }

    data class ProductUIState(
        var item: Item? = null,
    )
}