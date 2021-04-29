package com.apusart.cookit.ui.main_activity

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apusart.cookit.RecipeListQuery
import com.apusart.cookit.api.Resource
import com.apusart.cookit.api.remote.repositories.RecipeRepository
import com.apusart.cookit.tools.performRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
): ViewModel() {
    val currentItem = MutableLiveData(0)
    val searchText = MutableLiveData("")
    val isSearchOpen = MutableLiveData(false)
    val recipes = MutableLiveData<Resource<RecipeListQuery.Data>>()

    fun getRecipes() {
        viewModelScope.launch {
            recipes.postValue(recipeRepository.getAllRecipes())
        }
    }
}