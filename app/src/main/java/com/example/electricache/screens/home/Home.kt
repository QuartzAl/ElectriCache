package com.example.electricache.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.electricache.common.composables.CardDisplay
import com.example.electricache.theme.ElectriCacheTheme

@Composable
fun Home(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val totalItems by homeViewModel.totalItems.collectAsState()
    val lowStock by homeViewModel.lowStock.collectAsState()
    val uniqueItems by homeViewModel.uniqueItems.collectAsState()
    val randomItems by homeViewModel.randomItems.collectAsState()
    val lowStockAmount by homeViewModel.lowStockAmount.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row {
            Text(
                text = "Welcome, ",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "User",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {

            CardDisplay(title = "Total Items", bottomText = totalItems, modifier = Modifier.weight(1f))
            CardDisplay(title = "Total Projects", bottomText = "0", modifier = Modifier.weight(1f))

        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            CardDisplay(title = "Low Stock", bottomText = lowStockAmount, modifier = Modifier.weight(1f))
            CardDisplay(title = "Unique Items", bottomText = uniqueItems, modifier = Modifier.weight(1f))

        }


        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "3 Random Items of the day",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                items(randomItems) { item ->
                    RandomItemCard(item.name, item.quantity.toString())
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Low Stock Items",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (lowStock.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    items(lowStock) { item ->
                        LowStockItemCard(
                            item.name,
                            item.quantity.toString(),
                            item.lowStock.toString()
                        )
                    }
                }
            }
        } else {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "No low stock items, yay!",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun RandomItemCard(
    itemName: String,
    amount: String
){
    Row {
        Text(
            text = itemName,
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(text =  "Qty: $amount", style = MaterialTheme.typography.labelMedium)
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun LowStockItemCard(
    itemName: String,
    amount: String,
    lowStock: String
){
    Row (
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column (){
            Text(
                text = itemName,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Limit: $lowStock",
                style = MaterialTheme.typography.labelSmall
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(text =  "Qty: $amount", style = MaterialTheme.typography.labelMedium)
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    ElectriCacheTheme {
        LowStockItemCard(itemName = "item name", amount = "5", lowStock = "10")
    }
}