package com.example.electricache

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricache.theme.ElectriCacheTheme
import com.example.electricache.common.composables.CardDisplay
import com.example.electricache.common.Constants

class ItemDetailActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val name = intent.getStringExtra(Constants.ITEM_NAME)
        val description = intent.getStringExtra(Constants.ITEM_DESCRIPTION)
        val quantity = intent.getIntExtra(Constants.ITEM_QUANTITY, 0)
        val category = intent.getStringExtra(Constants.ITEM_CATEGORY)

        setContent {
            ElectriCacheTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = {
                                    Text(
                                        text = name ?: "",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
                                            .height(56.dp)
                                            .wrapContentHeight(Alignment.CenterVertically)
                                    )
                                },
                                navigationIcon = {
                                    IconButton(onClick = { finish() }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                },
                                actions = {
                                    IconButton(onClick = { finish() }) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Save"
                                        )
                                    }
                                }
                            )
                        }
                    ) {contentPadding ->
                        Column (
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(contentPadding)

                        ){
                            ItemDetail(
                                name = name ?: "",
                                description = description ?: "",
                                quantity = quantity,
                                category = category ?: ""
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun ItemDetail(
    name: String,
    description: String,
    quantity: Int,
    category: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Name",
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = name,
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            CardDisplay("Category", category, Modifier.weight(1f))
            CardDisplay("Quantity", quantity.toString(), Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Description",
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = description,
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium,
        )


    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    ElectriCacheTheme {
        ItemDetail(
            name = "Item Name",
            description = "Item Description",
            quantity = 1,
            category = "Item Category"
        )
    }
}