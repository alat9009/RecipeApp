package com.example.recipeapp.data.repository

import com.example.recipeapp.data.remote.api.SpoonacularApiService
import com.example.recipeapp.data.remote.model.RecipeDetailResponse
import com.example.recipeapp.data.remote.model.RecipeSearchItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val api: SpoonacularApiService
) {
    val apiKey: String = "9de5a1712e1c4cc389e9d617aa4a49fc"
    suspend fun searchRecipes(query: String): List<RecipeSearchItem> {

        val response = api.searchRecipes(
            query = query,
            apiKey = apiKey
        )
        return response.results
    }

    suspend fun getRecipeDetails(id: Int): RecipeDetailResponse {
        return api.getRecipeDetails(
            id = id,
            apiKey = apiKey
        )
    }
}
