package com.example.electricache.common.composables

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun DropDownMenu(
    @StringRes label: Int,
    options: List<String>,
    selection: String,
    modifier: Modifier,
    onNewValue: (String) -> Unit
) {
    Card(modifier = modifier) {
        DropdownSelector(label, options, selection, Modifier.fillMaxWidth(), onNewValue)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownContextMenu(
    options: List<String>,
    modifier: Modifier,
    onActionClick: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        modifier = modifier,
        onExpandedChange = { isExpanded = !isExpanded }
    ) {
        Icon(
            modifier = Modifier.padding(8.dp, 0.dp),
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More"
        )

        ExposedDropdownMenu(
            modifier = Modifier.width(180.dp),
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        isExpanded = false
                        onActionClick(selectionOption)
                    },
                    text = {
                        Text(text = selectionOption)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    @StringRes label: Int,
    options: List<String>,
    selection: String,
    modifier: Modifier,
    onNewValue: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        modifier = modifier,
        onExpandedChange = { isExpanded = !isExpanded }
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth().menuAnchor(),
            readOnly = true,
            value = selection,
            onValueChange = {},
            label = { Text(stringResource(label), maxLines = 1) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(isExpanded) },
            colors = dropdownColors(),
            maxLines = 1
        )

        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            options.forEach { selectionOption ->
                Log.d("test", "selectionOption: $selectionOption")
                DropdownMenuItem(
                    onClick = {
                        onNewValue(selectionOption)
                        isExpanded = false
                    },
                    text = {
                        Text(text = selectionOption)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun dropdownColors(): TextFieldColors {
    return ExposedDropdownMenuDefaults.textFieldColors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
        focusedLabelColor = MaterialTheme.colorScheme.primary,
        unfocusedLabelColor = MaterialTheme.colorScheme.primary
    )
}