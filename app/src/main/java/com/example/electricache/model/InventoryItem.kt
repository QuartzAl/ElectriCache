package com.example.electricache.model

import com.google.firebase.firestore.DocumentId


data class InventoryItem(
    @DocumentId val id: String = "",
    val name: String = "",
    val unit: String = "",
    val description: String = "",
    val lowStock: Int = 0,
    val quantity: Int = 0,
    val image: String = "",
    val partType: String = "",
    val mountType: String = "",
    val userId: String = ""

)
