package com.example.electricache.screens.add_item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricache.model.InventoryItem
import com.example.electricache.model.MountType
import com.example.electricache.model.PartType
import com.example.electricache.model.service.impl.StorageServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel
@Inject constructor(
    private val storageService: StorageServiceImpl
) : ViewModel() {

    private val _invItem = MutableStateFlow(InventoryItem())
    val invItem = _invItem.asStateFlow()

    private val _error = MutableStateFlow(ErrorUiState())
    val error = _error.asStateFlow()

    fun onLoad(id: String?) {
        if (id == null) {
            onPartTypeChange(PartType.None.toString())
            onMountTypeChange(MountType.None.toString())
            return
        }
        viewModelScope.launch {
            _invItem.value = storageService.getInventoryItem(id) ?: InventoryItem()
        }
    }

    fun onBack(popUp: () -> Unit) {
        popUp()
    }

    fun onNameChange(name: String) {
        _invItem.value = _invItem.value.copy(name = name)
    }

    fun onDescriptionChange(description: String) {
        _invItem.value = _invItem.value.copy(description = description)
    }

    fun onLowStockChange(lowStock: String) {
        if (lowStock.length > 9) return // prevent user from entering a number greater than 99999999
        _invItem.value = if (lowStock == "")
            _invItem.value.copy(lowStock = 0)
        else
            try {
                _invItem.value.copy(lowStock = lowStock.toInt())
            } catch (e: NumberFormatException) {
                return
            }
    }

    fun onQuantityChange(quantity: String) {
        if (quantity.length > 9) return // prevent user from entering a number greater than 99999999

        _invItem.value = if (quantity == "")
            _invItem.value.copy(quantity = 0)
        else
            try {
                _invItem.value.copy(quantity = quantity.toInt())
            } catch (e: NumberFormatException) {
                return
            }


    }

    fun onPartTypeChange(partType: String) {
        _invItem.value = _invItem.value.copy(partType = partType)
    }

    fun onMountTypeChange(mountType: String) {
        _invItem.value = _invItem.value.copy(mountType = mountType)
    }

    fun onSaveItem(popUp: () -> Unit) {
        _error.value = ErrorUiState()   // reset error state
        if (_invItem.value.name == "") {
            _error.value = _error.value.copy(nameError = "Name cannot be empty")
        }
        if (_invItem.value.quantity == 0) {
            _error.value = _error.value.copy(quantityError = "Quantity cannot be 0")
        }
        if (_error.value.nameError != "" || _error.value.quantityError != "") return
        if (_invItem.value.id == "") {
            viewModelScope.launch {
                storageService.saveItem(_invItem.value)
            }
        } else {
            viewModelScope.launch {
                storageService.updateItem(_invItem.value)
            }
        }

        popUp()
    }
}