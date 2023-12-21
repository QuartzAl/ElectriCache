package com.example.electricache.screens.add_item

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricache.R
import com.example.electricache.common.composables.DropDownMenu
import com.example.electricache.model.MountType
import com.example.electricache.theme.ElectriCacheTheme
import com.example.electricache.R.string as AppText

class AddItemActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElectriCacheTheme {
                val addItemViewModel by viewModels<AddItemViewModel>()

                val addItemUiState by addItemViewModel.invItem.collectAsState()
                var isExpanded by rememberSaveable {
                    mutableStateOf(false)
                }
                val invItem by addItemViewModel.invItem.collectAsState()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            CenterAlignedTopAppBar(
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    titleContentColor = MaterialTheme.colorScheme.primary,
                                ),
                                modifier = Modifier
                                    .height(56.dp),
                                title = {
                                    Text(
                                        text = "Add Item",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
                                            .height(56.dp)
                                            .wrapContentHeight(Alignment.CenterVertically)
                                    )
                                },
                                navigationIcon = {
                                    IconButton(
                                        onClick = { finish() },
                                        modifier = Modifier
                                            .height(56.dp)
                                            .wrapContentHeight(Alignment.CenterVertically)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBack,
                                            contentDescription = "Back",
                                        )
                                    }
                                },
                                actions = {
                                    IconButton(
                                        onClick = {
                                            addItemViewModel.onSaveItem()
                                            finish()
                                        },
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
                    ) { contentPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(contentPadding)
                                .padding(16.dp)
                        ) {
                            Spacer(Modifier.height(16.dp))
                            AddItemContent(
                                name = invItem.name,
                                quantity = invItem.quantity.toString(),
                                lowStock = invItem.lowStock.toString(),
                                description = invItem.description,
                                partType = invItem.partType,
                                partTypes = emptyList(),
                                mountType = invItem.mountType,
                                onNameChange = addItemViewModel::onNameChange,
                                onDescriptionChange = addItemViewModel::onDescriptionChange,
                                onQuantityChange = addItemViewModel::onQuantityChange,
                                onPartTypeChange = addItemViewModel::onPartTypeChange,
                                onMountTypeChange = addItemViewModel::onMountTypeChange,
                                onLowStockChange = addItemViewModel::onLowStockChange
                            )

                        }
                    }

                }
            }
        }
    }
}

@Composable
fun AddItemContent(
    name: String,
    quantity: String,
    lowStock: String,
    description: String,
    partType: String,
    mountType: String,
    partTypes: List<String>,
    onPartTypeChange: (String) -> Unit,
    onMountTypeChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onLowStockChange: (String) -> Unit
) {

    PartType(
        partTypes = partTypes,
        partType = partType,
        onPartTypeChange = onPartTypeChange,
        mountType = mountType,
        onMountTypeChange = onMountTypeChange
    )
    Spacer(modifier = Modifier.height(16.dp))
    NameField(name, onNameChange)
    Spacer(modifier = Modifier.height(16.dp))

    QuantityLowStockField(
        quantity = quantity,
        onQuantityChange = onQuantityChange,
        lowStock = lowStock,
        onLowStockChange = onLowStockChange
    )
    Spacer(modifier = Modifier.height(16.dp))
    DescriptionField(description, onDescriptionChange)
    Spacer(modifier = Modifier.height(8.dp))
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
    onLowStockChange: (String) -> Unit
) {
    Row {
        QuantityField(
            quantity = quantity,
            onQuantityChange = onQuantityChange,
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
    modifier: Modifier
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
fun PartType(
    partTypes: List<String>,
    partType: String,
    onPartTypeChange: (String) -> Unit,
    mountType: String,
    onMountTypeChange: (String) -> Unit
) {
    Row {
        DropDownMenu(
            label = AppText.part_type,
            options = partTypes,
            selection = partType,
            modifier = Modifier.weight(1f),
            onNewValue = onPartTypeChange
        )
        Spacer(modifier = Modifier.width(16.dp))
        DropDownMenu(
            label = AppText.mount_type,
            options = listOf("None", "SMD", "THT"),
            selection = mountType,
            modifier = Modifier.weight(1f)
        ) {newValue ->
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
            name = "Item Name",
            quantity = "1",
            lowStock = "5",
            description = "Description",
            partType = "Part Type",
            partTypes = emptyList(),
            mountType = "Mount Type",
            onPartTypeChange = {},
            onNameChange = {},
            onDescriptionChange = {},
            onQuantityChange = {},
            onLowStockChange = {},
            onMountTypeChange = {}
        )
    }
}