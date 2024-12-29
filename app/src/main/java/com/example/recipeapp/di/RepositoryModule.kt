package com.example.recipeapp.di

import com.example.recipeapp.data.repository.FavoriteRepository
import com.example.recipeapp.data.repository.RecipeRepository
import com.example.recipeapp.data.local.dao.FavoriteRecipeDao
import com.example.recipeapp.data.remote.api.SpoonacularApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRecipeRepository(
        api: SpoonacularApiService
    ): RecipeRepository {
        return RecipeRepository(api)
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        dao: FavoriteRecipeDao
    ): FavoriteRepository {
        return FavoriteRepository(dao)
    }
}
