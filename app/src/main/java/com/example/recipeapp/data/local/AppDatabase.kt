package com.example.recipeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipeapp.data.local.dao.FavoriteRecipeDao
import com.example.recipeapp.data.local.entity.FavoriteRecipe

@Database(
    entities = [FavoriteRecipe::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteRecipeDao(): FavoriteRecipeDao
}
