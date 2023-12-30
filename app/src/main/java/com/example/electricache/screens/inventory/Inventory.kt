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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun Inventory(
    inventoryViewModel: InventoryViewModel = hiltViewModel(),
    navigate: (String) -> Unit
) {

    val searchQuery by inventoryViewModel.searchQuery.collectAsState()
    val inventoryItemList by inventoryViewModel.itemListFiltered.collectAsState()

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            TextField(
                singleLine = true,
                value = searchQuery,
                onValueChange = inventoryViewModel::onSearchQueryChange,
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
                    onClick = { inventoryViewModel.onItemClicked(item.id, navigate) }
                )
            }
        }
    }
}