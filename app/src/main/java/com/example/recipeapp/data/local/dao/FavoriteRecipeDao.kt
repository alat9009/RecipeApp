package com.example.recipeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.data.local.entity.FavoriteRecipe
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteRecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(recipe: FavoriteRecipe)

    @Query("DELETE FROM favorite_recipes WHERE id = :recipeId")
    suspend fun removeFavorite(recipeId: Int)

    @Query("SELECT * FROM favorite_recipes")
    fun getAllFavorites(): Flow<List<FavoriteRecipe>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_recipes WHERE id = :recipeId)")
    suspend fun isFavorite(recipeId: Int): Boolean
}
