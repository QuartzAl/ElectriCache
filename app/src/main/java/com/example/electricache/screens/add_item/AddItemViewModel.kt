package com.example.electricache.screens.add_item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricache.model.InventoryItem
import com.example.electricache.model.service.impl.AccServiceImpl
import com.example.electricache.model.service.impl.StorageServiceImpl
import com.example.electricache.model.service.module.FirebaseModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class AddItemViewModel: ViewModel() {

    private val _intItem = MutableStateFlow(InventoryItem())
    val invItem = _intItem

    private val accountService = AccServiceImpl(FirebaseModule.auth())
    private val storageService = StorageServiceImpl(FirebaseModule.firestore(), accountService)

    fun onNameChange(name: String) {
        _intItem.value = _intItem.value.copy(name = name)
    }

    fun onDescriptionChange(description: String) {
        _intItem.value = _intItem.value.copy(description = description)
    }

    fun onLowStockChange(lowStock: String) {
        if (lowStock.contains(".")) return // prevent user from entering a decimal point
        _intItem.value = if (lowStock == "")
            _intItem.value.copy(lowStock = 0)
        else
            _intItem.value.copy(lowStock = lowStock.toInt())
    }

    fun onQuantityChange(quantity: String) {
        if (quantity.contains(".")) return // prevent user from entering a decimal point

        _intItem.value = if (quantity == "")
            _intItem.value.copy(quantity = 0)
        else
            _intItem.value.copy(quantity = quantity.toInt())
    }

    fun onPartTypeChange(partType: String) {
        _intItem.value = _intItem.value.copy(partType = partType)
    }

    fun onMountTypeChange(mountType: String) {
        _intItem.value = _intItem.value.copy(mountType = mountType)
    }

    fun onSaveItem() {
        viewModelScope.launch {
            storageService.saveItem(_intItem.value)
        }
    }
}