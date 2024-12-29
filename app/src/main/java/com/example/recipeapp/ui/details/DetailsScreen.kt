package com.example.recipeapp.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.recipeapp.data.remote.model.RecipeDetailResponse

@Composable
fun DetailsScreen(
    recipeId: Int,
    navController: NavHostController,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val recipeDetails by viewModel.recipeDetails.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

    // Fetch recipe details when this composable first launches
    LaunchedEffect(key1 = recipeId) {
        viewModel.fetchRecipeDetails(recipeId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            recipeDetails != null -> {
                RecipeDetailContent(
                    recipe = recipeDetails!!,
                    onBackClick = { navController.navigateUp() },
                    isFavorite = isFavorite,
                    onFavoriteToggle = {
                        viewModel.toggleFavorite(
                            recipeId,
                            recipeDetails!!.title,
                            recipeDetails!!.image
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun RecipeDetailContent(
    recipe: RecipeDetailResponse,
    onBackClick: () -> Unit,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = rememberAsyncImagePainter(recipe.image),
            contentDescription = recipe.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Ingredients", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        recipe.extendedIngredients.forEach { ingredient ->
            Text(text = "- ${ingredient.original}", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Instructions", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        recipe.instructions?.let {
            Text(text = it, style = MaterialTheme.typography.bodyLarge)
        } ?: Text(text = "No instructions available.", style = MaterialTheme.typography.bodyLarge)
    }
}
