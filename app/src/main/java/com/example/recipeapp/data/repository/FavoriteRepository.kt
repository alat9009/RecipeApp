package com.example.recipeapp.data.repository

import com.example.recipeapp.data.local.dao.FavoriteRecipeDao
import com.example.recipeapp.data.local.entity.FavoriteRecipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(
    private val dao: FavoriteRecipeDao
) {

    fun getAllFavorites(): Flow<List<FavoriteRecipe>> = dao.getAllFavorites()

    suspend fun addToFavorites(recipe: FavoriteRecipe) {
        dao.addFavorite(recipe)
    }

    suspend fun removeFromFavorites(recipeId: Int) {
        dao.removeFavorite(recipeId)
    }

    suspend fun isFavorite(recipeId: Int): Boolean {
        return dao.isFavorite(recipeId)
    }
}
