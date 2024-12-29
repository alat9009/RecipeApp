package com.example.recipeapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

val drawerItems = listOf(
    DrawerItem(
        title = "Home",
        icon = Icons.Filled.Home,
        route = Route.HOME
    ),
    DrawerItem(
        title = "Favorites",
        icon = Icons.Filled.Favorite,
        route = Route.FAVORITES
    ),
    DrawerItem(
        title = "Exit",
        icon = Icons.AutoMirrored.Filled.ExitToApp,
        route = "exit"
    )
)
