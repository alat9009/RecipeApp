package com.example.recipeapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.repository.RecipeRepository
import com.example.recipeapp.data.repository.FavoriteRepository
import com.example.recipeapp.data.local.entity.FavoriteRecipe
import com.example.recipeapp.data.remote.model.RecipeSearchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<RecipeSearchItem>>(emptyList())
    val searchResults: StateFlow<List<RecipeSearchItem>> = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchRecipes() {
        val query = _searchQuery.value.trim()
        if (query.isEmpty()) return

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val results = recipeRepository.searchRecipes(query)
                _searchResults.value = results
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching recipes: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addToFavorites(recipeId: Int, title: String, image: String?) {
        viewModelScope.launch {
            val favoriteRecipe = FavoriteRecipe(id = recipeId, title = title, image = image)
            favoriteRepository.addToFavorites(favoriteRecipe)
        }
    }
}
