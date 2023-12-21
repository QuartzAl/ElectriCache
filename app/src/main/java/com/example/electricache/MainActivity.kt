package com.example.electricache

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.electricache.screens.add_item.AddItemActivity
import com.example.electricache.screens.home.Home
import com.example.electricache.screens.inventory.Inventory
import com.example.electricache.screens.inventory.InventoryViewModel
import com.example.electricache.screens.projects.Projects
import com.example.electricache.screens.Settings
import com.example.electricache.theme.ElectriCacheTheme

data class BottomBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean = false,
    val badgeCount: Int? = null
)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val inventoryViewModel = InventoryViewModel()
            val searchQuery by inventoryViewModel.searchQuery.collectAsState()
            val itemList by inventoryViewModel.itemListFiltered.collectAsState()

            ElectriCacheTheme {
                // make a list of 4 items: Home, Inventory, Projects, and Settings
                val items = listOf(
                    BottomBarItem(
                        title = "Home",
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                        hasNews = true,
                        badgeCount = 2
                    ),
                    BottomBarItem(
                        title = "Inventory",
                        selectedIcon = Icons.Filled.Inventory2,
                        unselectedIcon = Icons.Outlined.Inventory2,
                        hasNews = false,
                        badgeCount = null
                    ),
                    BottomBarItem(
                        title = "Projects",
                        selectedIcon = Icons.Filled.Build,
                        unselectedIcon = Icons.Outlined.Build,
                        hasNews = false,
                        badgeCount = null
                    ),
                    BottomBarItem(
                        title = "Settings",
                        selectedIcon = Icons.Filled.Settings,
                        unselectedIcon = Icons.Outlined.Settings,
                        hasNews = false,
                        badgeCount = null
                    )
                )
                var selectedItemIndex by rememberSaveable {
                    mutableStateOf(0)
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            NavigationBar {
                                items.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        icon = {
                                            Icon(
                                                imageVector = if (index == selectedItemIndex) {
                                                    item.selectedIcon
                                                } else {
                                                    item.unselectedIcon
                                                },
                                                contentDescription = item.title
                                            )
                                        },
                                        label = {
                                            Text(text = item.title)
                                        },
                                        selected = index == selectedItemIndex,
                                        onClick = {
                                            selectedItemIndex = index
                                        },
                                        alwaysShowLabel = false
                                    )
                                }
                            }
                        },
                        floatingActionButton = {
                            if (items[selectedItemIndex].title == "Inventory") {
                                FloatingActionButton(
                                    content = {
                                        Icon(
                                            imageVector = Icons.Filled.Add,
                                            contentDescription = "Add"
                                        )
                                    },
                                    onClick = {
                                        val intent = Intent(this, AddItemActivity::class.java)
                                        startActivity(intent)
                                    }
                                )
                            }
                        }
                    ) { contentPadding ->
                        Surface(
                            modifier = Modifier
                                .padding(contentPadding)
                                .fillMaxSize(),
                            color = MaterialTheme.colorScheme.surface
                        ) {
                            when (items[selectedItemIndex].title) {
                                "Home" -> Home()
                                "Inventory" -> Inventory(
                                    inventoryItemList = itemList,
                                    searchQuery = searchQuery,
                                    onSearchQueryChange = { inventoryViewModel.onSearchQueryChange(it) }
                                )
                                "Projects" -> Projects()
                                "Settings" -> Settings()
                            }
                        }
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ElectriCacheTheme {
//        Inventory()
//    }
//}