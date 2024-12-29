package com.example.recipeapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.recipeapp.data.remote.model.RecipeSearchItem

@Composable
fun RecipeListItem(
    recipe: RecipeSearchItem,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        AsyncImage(
            model = recipe.image,
            contentDescription = recipe.title,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = recipe.title)
        }

        IconButton(onClick = onFavoriteClick) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Favorite"
            )
        }
    }
}

