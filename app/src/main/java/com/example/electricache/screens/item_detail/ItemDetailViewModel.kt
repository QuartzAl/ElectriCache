package com.example.electricache.screens.item_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricache.model.InventoryItem
import com.example.electricache.model.service.impl.AccServiceImpl
import com.example.electricache.model.service.impl.StorageServiceImpl
import com.example.electricache.model.service.module.FirebaseModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ItemDetailViewModel: ViewModel() {

    private val _invItem = MutableStateFlow(InventoryItem())
    val invItem = _invItem

    private val _closeScreen = MutableStateFlow(false)
    val closeScreen = _closeScreen

    private val accountService = AccServiceImpl(FirebaseModule.auth())
    private val storageService = StorageServiceImpl(FirebaseModule.firestore(), accountService)

    fun onStart(id: String?) {
        if (id == null) return
        Log.d("test", "onStart: $id")
        viewModelScope.launch {
            _invItem.value = storageService.getInventoryItem(id) ?: InventoryItem()
        }
    }

    fun onDelete(id: String?) {
        if (id == null) return
        viewModelScope.launch {
            storageService.deleteItem(id)
            _closeScreen.value = true
        }

    }
}