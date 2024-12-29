package com.example.recipeapp.data.remote.api

import com.example.recipeapp.data.remote.model.RecipeDetailResponse
import com.example.recipeapp.data.remote.model.RecipeSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpoonacularApiService {

    // Search recipes by name
    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String
    ): RecipeSearchResponse


     //Get recipe details by ID

    @GET("recipes/{id}/information")
    suspend fun getRecipeDetails(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String,
        @Query("includeNutrition") includeNutrition: Boolean = false
    ): RecipeDetailResponse
}
