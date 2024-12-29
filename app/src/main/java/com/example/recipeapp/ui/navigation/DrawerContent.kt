package com.example.recipeapp.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent(
    items: List<DrawerItem>,
    onItemClick: (DrawerItem) -> Unit,
    currentRoute: String = Route.HOME
) {
    Column(modifier = Modifier.fillMaxSize()) {
        DrawerHeader()
        Spacer(modifier = Modifier.height(16.dp))
        items.forEach { item ->
            DrawerItemRow(
                item = item,
                onClick = { onItemClick(item) },
                isSelected = item.route == currentRoute
            )
        }
    }
}

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "RecipeApp",
            style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onPrimary)
        )
    }
}

@Composable
fun DrawerItemRow(item: DrawerItem, onClick: () -> Unit, isSelected: Boolean) {
    val background = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else Color.Transparent
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(background)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            tint = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = item.title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
