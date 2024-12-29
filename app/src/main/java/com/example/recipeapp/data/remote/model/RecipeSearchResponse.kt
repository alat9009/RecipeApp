package com.example.recipeapp.data.remote.model

import com.google.gson.annotations.SerializedName

 //Represents the JSON returned by Spoonacular

data class RecipeSearchResponse(
    @SerializedName("results")
    val results: List<RecipeSearchItem>,

    @SerializedName("offset")
    val offset: Int,

    @SerializedName("number")
    val number: Int,

    @SerializedName("totalResults")
    val totalResults: Int

)

//info from the search results array

data class RecipeSearchItem(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("image")
    val image: String?
)
