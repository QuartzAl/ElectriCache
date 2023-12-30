package com.example.electricache.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricache.model.InventoryItem
import com.example.electricache.model.service.impl.StorageServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject constructor(
        private val storageService: StorageServiceImpl
    )
    : ViewModel() {

    private val _totalItems = MutableStateFlow("0")
    var totalItems = storageService.items.map {
        _totalItems.value = it.sumOf { item -> item.quantity }.toString()
        _totalItems.value
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = "0"
    )

    private val _lowStock = MutableStateFlow(listOf(InventoryItem()))
    var lowStock = storageService.items.map {
        _lowStock.value = it.filter { item -> item.quantity < item.lowStock }
        _lowStock.value
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = listOf(InventoryItem())
    )

    private val _lowStockAmount = MutableStateFlow("0")
    var lowStockAmount = storageService.items.map {
        _lowStockAmount.value = it.filter { item -> item.quantity < item.lowStock }.size.toString()
        _lowStockAmount.value
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = "0"
    )

    private val _uniqueItems = MutableStateFlow("0")
    var uniqueItems= storageService.items.map {
        _totalItems.value = it.size.toString()
        _totalItems.value
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = "0"
    )

    private val _randomItems = MutableStateFlow(emptyList<InventoryItem>())
    var randomItems = storageService.items.map {
        _randomItems.value = it.shuffled().take(3)
        _randomItems.value
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = emptyList()
    )

    private val items = storageService.items



}
