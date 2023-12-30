package com.example.electricache

import android.content.res.Resources
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.electricache.screens.Settings
import com.example.electricache.screens.add_item.AddItem
import com.example.electricache.screens.home.Home
import com.example.electricache.screens.inventory.Inventory
import com.example.electricache.screens.item_detail.ItemDetailScreen
import com.example.electricache.screens.projects.Projects
import com.example.electricache.screens.splash.SplashScreen
import com.example.makeitso.common.snackbar.SnackbarManager
import kotlinx.coroutines.CoroutineScope

@Composable
fun ElectriCacheApp(

) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController  = rememberNavController()
        val appState = rememberAppState(
            navController = navController
        )
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                ElectriCacheBottomBar(navController = navController)
            },
            floatingActionButton = {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                if (currentDestination?.hierarchy?.any { it.route == INVENTORY_SCREEN } == true) {
                    FloatingActionButton(
                        content = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add"
                            )
                        },
                        onClick = {
                            navController.navigate(ADD_ITEM_SCREEN)
                        }
                    )
                }
            }
        ) { contentPadding ->
            NavHost(
                navController = navController,
                startDestination = SPLASH_SCREEN,
                modifier = Modifier.padding(contentPadding)
            ) {
                electriCacheGraph(appState)
            }
        }
    }
}

fun NavGraphBuilder.electriCacheGraph(appState: ElectriCacheAppState) {
    composable(SPLASH_SCREEN) {
        SplashScreen(
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }
        )
    }
    composable(HOME_SCREEN) {
        Home()
    }
    composable(INVENTORY_SCREEN) {
        Inventory(navigate = { route -> appState.navigate(route) })
    }
    composable(PROJECTS_SCREEN) {
        Projects()
    }
    composable(SETTINGS_SCREEN) {
        Settings()
    }
    composable(ADD_ITEM_SCREEN) {
        AddItem(
            popUp = { appState.popUp() },
            id = null
        )
    }
    composable(
        "$ADD_ITEM_SCREEN/{itemId}",
        arguments = listOf(navArgument("itemId") { type = NavType.StringType })
    ) {
        AddItem(
            popUp = { appState.popUp() },
            id = it.arguments?.getString("itemId")
        )
    }
    composable(
        "$ITEM_DETAIL_SCREEN/{itemId}",
        arguments = listOf(navArgument("itemId") { type = NavType.StringType })
    ) {navBackStackEntry ->
        ItemDetailScreen(
            id = navBackStackEntry.arguments?.getString("itemId"),
            popUp = { appState.popUp() },
            navigate = { route -> appState.navigate(route) }
        )
    }
}

@Composable
fun ElectriCacheBottomBar(navController: NavHostController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector =
                        if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                            screen.item.selectedIcon
                        } else {
                            screen.item.unselectedIcon
                        },
                        contentDescription = screen.item.title
                    )
                },
                label = {
                    Text(text = screen.item.title)
                },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                alwaysShowLabel = false
            )
        }
    }
}

@Composable
fun rememberAppState(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(snackbarHostState, navController, snackbarManager, resources, coroutineScope) {
        ElectriCacheAppState(snackbarHostState, navController, snackbarManager, resources, coroutineScope)
    }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

val items = listOf(
    ElectriCacheScreen.Home,
    ElectriCacheScreen.Inventory,
    ElectriCacheScreen.Projects,
    ElectriCacheScreen.Settings
)

sealed class ElectriCacheScreen(val route: String, val item: BottomBarItem) {
    data object Home : ElectriCacheScreen(HOME_SCREEN,
        BottomBarItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ))
    data object Inventory : ElectriCacheScreen(INVENTORY_SCREEN,
        BottomBarItem(
            title = "Inventory",
            selectedIcon = Icons.Filled.Inventory2,
            unselectedIcon = Icons.Outlined.Inventory2,
        ))
    data object Projects : ElectriCacheScreen(PROJECTS_SCREEN,
        BottomBarItem(
            title = "Projects",
            selectedIcon = Icons.Filled.Build,
            unselectedIcon = Icons.Outlined.Build,
        ))
    data object Settings : ElectriCacheScreen(SETTINGS_SCREEN,
        BottomBarItem(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
        ))
}
