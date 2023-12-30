package com.example.electricache.screens.item_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.electricache.common.composables.CardDisplay
import com.example.electricache.model.InventoryItem
import com.example.electricache.theme.ElectriCacheTheme

@Composable
fun ItemDetailScreen(
    itemDetailViewModel: ItemDetailViewModel =  hiltViewModel(),
    id: String?,
    popUp: () -> Unit,
    navigate: (String) -> Unit
){
    itemDetailViewModel.onLoad(id)
    val invItem by itemDetailViewModel.invItem.collectAsState()
    val showDialog by itemDetailViewModel.showDialog.collectAsState()
    if (showDialog) {
        WarnDelete(
            onDelete = { itemDetailViewModel.onDelete(popUp) },
            onDismiss = itemDetailViewModel::onDismiss
        )
    }
    Scaffold(
        topBar = {
            ItemDetailTopBar(
                itemName = invItem.name,
                onBack = { itemDetailViewModel.onBack(popUp) },
                onRequestDelete = itemDetailViewModel::onRequestDelete,
                onRequestEdit = {itemDetailViewModel.onRequestEdit(navigate)},
            )
        }
    ) {contentPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)

        ){
            ItemDetail(
                item = invItem
            )
        }

    }
}

@Composable
fun WarnDelete(
    onDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Delete Item") },
        text = { Text("Are you sure you want to delete this item?") },
        confirmButton = {
            Button(
                onClick = onDelete
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("No")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailTopBar(
    itemName: String,
    onBack: () -> Unit,
    onRequestDelete: () -> Unit,
    onRequestEdit: () -> Unit
){
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = itemName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .height(56.dp)
                    .wrapContentHeight(Alignment.CenterVertically)
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = { onRequestEdit() }){
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit"
                )
            }
            IconButton(onClick = { onRequestDelete() }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }

        }
    )
}

@Composable
fun ItemDetail(
    item: InventoryItem
) {
    val lowStockString = if (item.lowStock == 0) {
        "None"
    } else {
        item.lowStock.toString()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Name",
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.name,
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.labelLarge,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            CardDisplay("Category", item.partType, Modifier.weight(1f))
            CardDisplay("Mount Type", item.mountType, Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            CardDisplay("Quantity", item.quantity.toString(), Modifier.weight(1f))
            CardDisplay("Low Stock limit", lowStockString, Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (item.description != "") {
            Text(
                text = "Description",
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.description,
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.labelMedium,
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
fun ItemDetailPreview() {
    ElectriCacheTheme {
        ItemDetail(
            item = InventoryItem(
                id = "1",
                name = "Test",
                partType = "Test",
                mountType = "Test",
                quantity = 1,
                lowStock = 1,
                description = "Test"
            )
        )
    }
}