package com.example.electricache.screens.inventory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.electricache.model.InventoryItem
import com.example.electricache.common.composables.ItemCard


@Composable
fun Inventory(
    inventoryItemList: List<InventoryItem>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            TextField(
                singleLine = true,
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(text = "Search")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Inventory2,
                        contentDescription = "Search"
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(inventoryItemList) { item ->
                ItemCard(
                    inventoryItem = item,
                )
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun InventoryPreview() {
//    ElectriCacheTheme {
//        Inventory()
//    }
//}