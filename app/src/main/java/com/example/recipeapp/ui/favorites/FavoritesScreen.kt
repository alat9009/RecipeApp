package com.example.recipeapp.ui.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.recipeapp.data.local.entity.FavoriteRecipe
import com.example.recipeapp.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavHostController,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoriteRecipes by viewModel.favoriteRecipes.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Favorites") })
        }
    ) { paddingValues ->
        if (favoriteRecipes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No favorites yet.")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(favoriteRecipes) { recipe ->
                    FavoriteRecipeItem(
                        recipe = recipe,
                        onClick = {
                            navController.navigate(Screen.Details.createRoute(recipe.id))
                        },
                        onDeleteClick = {
                            viewModel.removeFromFavorites(recipe.id)
                        }
                    )
                    HorizontalDivider(modifier = Modifier.width(10.dp))
                }
            }
        }
    }
}

@Composable
fun FavoriteRecipeItem(
    recipe: FavoriteRecipe,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Image(
            painter = rememberImagePainter(recipe.image),
            contentDescription = recipe.title,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = recipe.title, style = MaterialTheme.typography.titleSmall)
        }

        IconButton(onClick = onDeleteClick) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove from Favorites")
        }
    }
}
