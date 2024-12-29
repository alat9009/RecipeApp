package com.example.recipeapp.data.remote.model

import com.google.gson.annotations.SerializedName

//Represents the JSON returned by Spoonacular

data class RecipeDetailResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("image")
    val image: String?,

    @SerializedName("extendedIngredients")
    val extendedIngredients: List<ExtendedIngredient>,

    @SerializedName("instructions")
    val instructions: String?
)

// Represents individual ingredient information

data class ExtendedIngredient(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("amount")
    val amount: Double,

    @SerializedName("unit")
    val unit: String,

    @SerializedName("original")
    val original: String
)
