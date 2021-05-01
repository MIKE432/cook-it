package com.apusart.cookit.ui.fragments.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.ApolloClient
import com.apusart.cookit.RecipeListQuery
import com.apusart.cookit.RecipeScopedListQuery
import com.apusart.cookit.api.Resource
import com.apusart.cookit.api.remote.repositories.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(private val recipeRepository: RecipeRepository) :
    ViewModel() {
    companion object {
        const val limit = 20
    }

    val recipes = MutableLiveData<Resource<RecipeScopedListQuery.Data>>()
    val offset = MutableLiveData(0)

    fun getRecipesByName(name: String) {
        viewModelScope.launch {
            recipes.value = Resource.pending()
            recipes.value = recipeRepository.getRecipes(offset.value!!, limit + offset.value!!)
        }
    }
}