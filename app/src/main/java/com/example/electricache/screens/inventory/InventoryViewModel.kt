package com.example.electricache.screens.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricache.model.service.impl.AccServiceImpl
import com.example.electricache.model.service.impl.StorageServiceImpl
import com.example.electricache.model.service.module.FirebaseModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class InventoryViewModel: ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val accountService = AccServiceImpl(FirebaseModule.auth())
    private val storageService = StorageServiceImpl(FirebaseModule.firestore(), accountService)


    private val _itemList = storageService.items.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = emptyList()
    )

    val itemListFiltered = searchQuery.combine(_itemList) { query, items ->
        if (query.isBlank()) {
            items
        } else {
            items.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.partType.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = _itemList.value
    )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
}