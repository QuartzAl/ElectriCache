package com.example.electricache.model.service


import com.example.electricache.model.InventoryItem
import kotlinx.coroutines.flow.Flow

interface StorageService {
    val items: Flow<List<InventoryItem>>
    suspend fun getInventoryItem(itemId: String): InventoryItem?
    suspend fun saveItem(item: InventoryItem): String
    suspend fun updateItem(item: InventoryItem)
    suspend fun deleteItem(itemId: String)
}