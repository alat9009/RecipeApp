package com.example.recipeapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.recipeapp.ui.components.RecipeListItem

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            label = { Text("Search Recipes") },
            trailingIcon = {
                IconButton(onClick = { viewModel.searchRecipes() }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Loading indicator
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        // Error Message
        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        //Display Results
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(searchResults, key = { it.id }) { recipe ->
                RecipeListItem(
                    recipe = recipe,
                    onClick = {
                        navController.navigate("details/${recipe.id}")
                    },
                    onFavoriteClick = {
                        viewModel.addToFavorites(recipe.id, recipe.title, recipe.image)
                    }
                )
                Divider()
            }
        }
    }
}
