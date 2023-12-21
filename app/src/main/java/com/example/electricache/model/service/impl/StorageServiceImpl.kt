package com.example.electricache.model.service.impl

import androidx.compose.ui.util.trace
import com.example.electricache.model.InventoryItem
import com.example.electricache.model.service.AccountService
import com.example.electricache.model.service.StorageService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl
@Inject
constructor(private val firestore: FirebaseFirestore, private val auth: AccountService) :
    StorageService {
    @OptIn(ExperimentalCoroutinesApi::class)
    override val items: Flow<List<InventoryItem>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                firestore.collection(ITEM_COLLECTION)
                    .whereEqualTo(USER_ID_FIELD, user.id)
                    .dataObjects()
            }

    override suspend fun getInventoryItem(itemId: String): InventoryItem? =
        firestore.collection(ITEM_COLLECTION).document(itemId).get().await().toObject()

    override suspend fun saveItem(item: InventoryItem): String =
        trace(SAVE_ITEM_TRACE) {
            val itemWithUserId = item.copy(userId = auth.currentUserId)
            firestore.collection(ITEM_COLLECTION).add(itemWithUserId).await().id
        }

    override suspend fun updateItem(item: InventoryItem) : Unit =
        trace(UPDATE_ITEM_TRACE) {
            firestore.collection(ITEM_COLLECTION).document(item.id).set(item).await()
        }

    override suspend fun deleteItem(itemId: String) {
        firestore.collection(ITEM_COLLECTION).document(itemId).delete().await()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val ITEM_COLLECTION = "items"
        private const val SAVE_ITEM_TRACE = "saveItem"
        private const val UPDATE_ITEM_TRACE = "updateItem"
    }
}