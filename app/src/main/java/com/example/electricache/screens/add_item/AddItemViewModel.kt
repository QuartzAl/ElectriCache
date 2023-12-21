package com.example.electricache.screens.add_item

import androidx.lifecycle.ViewModel
import com.example.electricache.model.InventoryItem
import kotlinx.coroutines.flow.MutableStateFlow


class AddItemViewModel: ViewModel() {

    private val _InvItem = MutableStateFlow(InventoryItem())
    val invItem = _InvItem

    fun onNameChange(name: String) {
        _InvItem.value = _InvItem.value.copy(name = name)
    }

    fun onDescriptionChange(description: String) {
        _InvItem.value = _InvItem.value.copy(description = description)
    }

    fun onLowStockChange(lowStock: String) {
        if (lowStock.contains(".")) return // prevent user from entering a decimal point
        _InvItem.value = if (lowStock == "")
            _InvItem.value.copy(lowStock = 0)
        else
            _InvItem.value.copy(lowStock = lowStock.toInt())
    }

    fun onQuantityChange(quantity: String) {
        if (quantity.contains(".")) return // prevent user from entering a decimal point

        _InvItem.value = if (quantity == "")
            _InvItem.value.copy(quantity = 0)
        else
            _InvItem.value.copy(quantity = quantity.toInt())
    }

    fun onPartTypeChange(partType: String) {
        _InvItem.value = _InvItem.value.copy(partType = partType)
    }

    fun onMountTypeChange(mountType: String) {
        _InvItem.value = _InvItem.value.copy(mountType = mountType)
    }

    fun onSaveItem() {
        // TODO: save item to database
    }


}

// make list of categories
private val categoriesDummy = listOf(
    "Microcontroller",
    "Sensor",
    "Actuator",
    "Power Supply",
    "Other"
)