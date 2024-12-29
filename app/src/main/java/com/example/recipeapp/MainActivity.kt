package com.example.recipeapp

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.recipeapp.ui.details.DetailsScreen
import com.example.recipeapp.ui.favorites.FavoritesScreen
import com.example.recipeapp.ui.home.HomeScreen
import com.example.recipeapp.ui.navigation.*
import com.example.recipeapp.ui.theme.RecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(navController: NavHostController) {
    // Initialize the drawer state
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    // Observe the current back stack entry to determine the current route
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: Route.HOME

    // State for showing the Exit Confirmation Dialog
    var showExitDialog by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                items = drawerItems,
                onItemClick = { drawerItem ->
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    when (drawerItem.route) {
                        "exit" -> {
                            showExitDialog = true
                        }
                        else -> {
                            if (drawerItem.route != currentRoute) {
                                navController.navigate(drawerItem.route) {
                                    popUpTo(Route.HOME) {
                                        inclusive = false
                                    }
                                    launchSingleTop = true
                                }
                            }
                        }
                    }
                },
                currentRoute = currentRoute
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("RecipeApp") },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Route.HOME,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Route.HOME) {
                    HomeScreen(navController = navController)
                }
                composable(Route.FAVORITES) {
                    FavoritesScreen(navController = navController)
                }
                composable(Route.DETAILS) { backStackEntry ->
                    val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull()
                    recipeId?.let {
                        DetailsScreen(recipeId = it, navController = navController)
                    }
                }
                composable("exit") {
                }
            }

            if (showExitDialog) {
                val context = LocalContext.current
                ExitConfirmationDialog(
                    onConfirm = {
                        showExitDialog = false
                        (context as? Activity)?.finish()
                    },
                    onDismiss = { showExitDialog = false }
                )
            }
        }
    }
}


@Composable
fun ExitConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Exit App") },
        text = { Text("Are you sure you want to exit the app?") },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("No")
            }
        }
    )
}

