package com.example.electricache.screens.add_item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.electricache.common.composables.DropDownMenu
import com.example.electricache.model.InventoryItem
import com.example.electricache.model.MountType
import com.example.electricache.model.PartType
import com.example.electricache.theme.ElectriCacheTheme
import com.example.electricache.R.string as AppText

@Composable
fun AddItem(
    popUp: () -> Unit,
    addItemViewModel: AddItemViewModel = hiltViewModel(),
    id: String?
) {
    addItemViewModel.onLoad(id)
    val invItem by addItemViewModel.invItem.collectAsState()
    val error by addItemViewModel.error.collectAsState()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AddItemTopBar(
                onSaveItem = { addItemViewModel.onSaveItem(popUp) },
                onBack = { addItemViewModel.onBack(popUp) },
                isEdit = id != null
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AddItemContent(
                item = invItem,
                partTypes = PartType.getOptions(),
                onPartTypeChange = addItemViewModel::onPartTypeChange,
                onMountTypeChange = addItemViewModel::onMountTypeChange,
                onNameChange = addItemViewModel::onNameChange,
                onDescriptionChange = addItemViewModel::onDescriptionChange,
                onQuantityChange = addItemViewModel::onQuantityChange,
                onLowStockChange = addItemViewModel::onLowStockChange,
                error = error
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemTopBar(
    onSaveItem: () -> Unit,
    onBack: () -> Unit,
    isEdit: Boolean
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = Modifier.height(56.dp),
        title = {
            Text(
                text = if (isEdit) "Edit Item" else "Add Item",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .height(56.dp)
                    .wrapContentHeight(Alignment.CenterVertically)
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { onBack() },
                modifier = Modifier
                    .height(56.dp)
                    .wrapContentHeight(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        },
        actions = {
            IconButton(
                onClick = { onSaveItem() },
                modifier = Modifier
                    .height(56.dp)
                    .wrapContentHeight(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Save",
                )
            }
        }
    )
}

@Composable
fun AddItemContent(
    item: InventoryItem,
    partTypes: List<String>,
    onPartTypeChange: (String) -> Unit,
    onMountTypeChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onLowStockChange: (String) -> Unit,
    error: ErrorUiState
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        PartTypeSection(
            partTypes = partTypes,
            partType = item.partType,
            onPartTypeChange = onPartTypeChange,
            mountType = item.mountType,
            onMountTypeChange = onMountTypeChange
        )
        Spacer(modifier = Modifier.height(16.dp))
        NameField(item.name, onNameChange)
        if (error.nameError != "") {
            Text(
                text = error.nameError,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))

        QuantityLowStockField(
            quantity = item.quantity.toString(),
            onQuantityChange = onQuantityChange,
            lowStock = item.lowStock.toString(),
            onLowStockChange = onLowStockChange,
            error = error
        )
        Spacer(modifier = Modifier.height(16.dp))
        DescriptionField(item.description, onDescriptionChange)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun NameField(
    name: String,
    onNameChange: (String) -> Unit
) {
    Text(
        text = "Name",
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
    TextField(
        value = name,
        onValueChange = onNameChange,
        placeholder = { Text("Item Name") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun QuantityLowStockField(
    quantity: String,
    onQuantityChange: (String) -> Unit,
    lowStock: String,
    onLowStockChange: (String) -> Unit,
    error: ErrorUiState
) {
    Row {
        QuantityField(
            quantity = quantity,
            onQuantityChange = onQuantityChange,
            error = error,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        LowStockField(
            lowStock = lowStock,
            onLowStockChange = onLowStockChange,
            modifier = Modifier.weight(1f)
        )

    }
}

@Composable
fun QuantityField(
    quantity: String,
    onQuantityChange: (String) -> Unit,
    modifier: Modifier,
    error: ErrorUiState
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Quantity",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        TextField(
            value = quantity,
            onValueChange = onQuantityChange,
            placeholder = { Text("1") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            )
        )
        if (error.quantityError != ""){
            Text(
                text = error.quantityError,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun LowStockField(
    lowStock: String,
    onLowStockChange: (String) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Low Stock",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        TextField(
            value = lowStock,
            onValueChange = onLowStockChange,
            placeholder = { Text("5") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            )
        )
    }
}

@Composable
fun PartTypeSection(
    partTypes: List<String>,
    partType: String,
    onPartTypeChange: (String) -> Unit,
    mountType: String,
    onMountTypeChange: (String) -> Unit
) {
    Row {
        DropDownMenu(
            label = AppText.part_type,
            options = PartType.getOptions(),
            selection = partType,
            modifier = Modifier.weight(1f),
            onNewValue = onPartTypeChange
        )
        Spacer(modifier = Modifier.width(16.dp))
        DropDownMenu(
            label = AppText.mount_type,
            options = MountType.getOptions(),
            selection = mountType,
            modifier = Modifier.weight(1f)
        ) { newValue ->
            onMountTypeChange(newValue)
        }
    }
}

@Composable
fun DescriptionField(
    description: String,
    onDescriptionChange: (String) -> Unit
) {
    Text(
        text = "Description",
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
    TextField(
        value = description,
        onValueChange = onDescriptionChange,
        placeholder = { Text("Very cool item") },
        modifier = Modifier.fillMaxWidth(),
        maxLines = 3
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ElectriCacheTheme {
        AddItemContent(
            item = InventoryItem(
                name = "Item Name",
                description = "Item Description",
                quantity = 1,
                partType = PartType.Crystal.toString(),
                mountType = MountType.SMD.toString(),
                lowStock = 5
            ),
            partTypes = PartType.getOptions(),
            onPartTypeChange = {},
            onMountTypeChange = {},
            onNameChange = {},
            onDescriptionChange = {},
            onQuantityChange = {},
            onLowStockChange = {},
            error = ErrorUiState()
        )
    }
}
