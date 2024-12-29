package com.example.recipeapp.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.local.entity.FavoriteRecipe
import com.example.recipeapp.data.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    val favoriteRecipes: StateFlow<List<FavoriteRecipe>> = favoriteRepository.getAllFavorites()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun removeFromFavorites(recipeId: Int) {
        viewModelScope.launch {
            favoriteRepository.removeFromFavorites(recipeId)
        }
    }
}
