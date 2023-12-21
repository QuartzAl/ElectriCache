package com.example.electricache.common.composables

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricache.common.Constants.ITEM_ID
import com.example.electricache.screens.item_detail.ItemDetailActivity
import com.example.electricache.model.InventoryItem

@Composable
fun ItemCard(inventoryItem: InventoryItem) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
               val intent = Intent(
                   context,
                   ItemDetailActivity::class.java)
                intent.putExtra(ITEM_ID, inventoryItem.id)
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(8.dp)
    ) {
        Row {
            Column(
                modifier = Modifier
                    .padding(16.dp)

            ) {
                Text(
                    text = inventoryItem.name,
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = inventoryItem.description,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.width(180.dp),
                    maxLines = 2,
                    softWrap = true,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.End,

            ) {
                Text(
                    text = "Qty: " + inventoryItem.quantity.toString(),
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = inventoryItem.partType,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemCardPreview() {
    ItemCard(
        InventoryItem(
        name = "Item Name",
        quantity = 1,
        description = "Description",
        partType = "Category",
        image = "Test",
        id = "1"
    )
    )
}