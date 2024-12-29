package com.example.recipeapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.local.entity.FavoriteRecipe
import com.example.recipeapp.data.remote.model.RecipeDetailResponse
import com.example.recipeapp.data.repository.RecipeRepository
import com.example.recipeapp.data.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _recipeDetails = MutableStateFlow<RecipeDetailResponse?>(null)
    val recipeDetails: StateFlow<RecipeDetailResponse?> = _recipeDetails.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun fetchRecipeDetails(recipeId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val details = recipeRepository.getRecipeDetails(recipeId)
                _recipeDetails.value = details
                _isFavorite.value = favoriteRepository.isFavorite(recipeId)
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching recipe details: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite(recipeId: Int, title: String, image: String?) {
        viewModelScope.launch {
            if (_isFavorite.value) {
                favoriteRepository.removeFromFavorites(recipeId)
                _isFavorite.value = false
            } else {
                val favoriteRecipe = FavoriteRecipe(id = recipeId, title = title, image = image)
                favoriteRepository.addToFavorites(favoriteRecipe)
                _isFavorite.value = true
            }
        }
    }
}
