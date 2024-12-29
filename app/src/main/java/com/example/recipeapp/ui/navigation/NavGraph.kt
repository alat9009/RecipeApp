// File: app/src/main/java/com/example/recipeapp/ui/navigation/NavGraph.kt

package com.example.recipeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.recipeapp.ui.details.DetailsScreen
import com.example.recipeapp.ui.favorites.FavoritesScreen
import com.example.recipeapp.ui.home.HomeScreen

@Composable
fun RecipeNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        // Home Screen
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        // Favorites Screen
        composable(Screen.Favorites.route) {
            FavoritesScreen(navController = navController)
        }

        // Details Screen with recipeId as an argument
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId")
            if (recipeId != null) {
                DetailsScreen(recipeId = recipeId, navController = navController)
            }
        }
    }
}
