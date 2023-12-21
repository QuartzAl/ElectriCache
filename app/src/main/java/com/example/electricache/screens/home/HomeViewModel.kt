package com.example.electricache.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricache.model.InventoryItem
import com.example.electricache.model.service.impl.AccServiceImpl
import com.example.electricache.model.service.impl.StorageServiceImpl
import com.example.electricache.model.service.module.FirebaseModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel : ViewModel() {
    private val _totalItems = MutableStateFlow("0")
    lateinit var totalItems: StateFlow<String>

    private val _lowStock = MutableStateFlow("0")
    lateinit var lowStock: StateFlow<String>

    private val _uniqueItems = MutableStateFlow("0")
    lateinit var uniqueItems: StateFlow<String>

    private val _threeRandomItems = MutableStateFlow(emptyList<InventoryItem>())
    lateinit var threeRandomItems: StateFlow<List<InventoryItem>>

    private val accountService = AccServiceImpl(FirebaseModule.auth())
    private val storageService = StorageServiceImpl(FirebaseModule.firestore(), accountService)

    private val items = storageService.items

    fun onStart() {
        totalItems = items.map { it.sumOf { item -> item.quantity }.toString() }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = "0"
        )
        lowStock = items.map {
            it.filter { item ->
                item.lowStock >= item.quantity
            }.count().toString()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = "0"
        )

        uniqueItems = items.map { it.size.toString() }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = "0"
        )

        threeRandomItems = items.map { it.shuffled().take(3) }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = emptyList()
        )
        Log.d("test", "onStart: ${threeRandomItems.value}")
        Log.d("test", "onStart: ${uniqueItems.value}")
        Log.d("test", "onStart: ${lowStock.value}")
        Log.d("test", "onStart: ${totalItems.value}")
    }

}
