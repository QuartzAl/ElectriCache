package com.example.electricache.screens.item_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricache.ADD_ITEM_SCREEN
import com.example.electricache.model.InventoryItem
import com.example.electricache.model.service.impl.StorageServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemDetailViewModel
    @Inject constructor(
        private val storageService: StorageServiceImpl
    )
    : ViewModel() {

    private val _invItem = MutableStateFlow(InventoryItem())
    val invItem = _invItem.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog = _showDialog.asStateFlow()

    fun onLoad(id: String?) {
        if (id == null) return
        Log.d("test", "onStart: $id")
        viewModelScope.launch {
            _invItem.value = storageService.getInventoryItem(id) ?: InventoryItem()
        }
    }

    fun onBack(popUp: () -> Unit) {
        popUp()
    }

    fun onRequestDelete() {
        _showDialog.value = true
    }

    fun onRequestEdit(navigate: (String) -> Unit) {
        navigate("$ADD_ITEM_SCREEN/${_invItem.value.id}")
    }

    fun onDelete(popUp: () -> Unit) {
        viewModelScope.launch {
            storageService.deleteItem(_invItem.value.id)
        }
        _showDialog.value = false
        popUp()
    }

    fun onDismiss() {
        _showDialog.value = false
    }
}