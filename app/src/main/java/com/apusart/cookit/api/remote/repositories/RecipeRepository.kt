package com.apusart.cookit.api.remote.repositories

import com.apollographql.apollo.ApolloClient
import com.apusart.cookit.RecipeListQuery
import com.apusart.cookit.RecipeScopedListQuery
import com.apusart.cookit.api.Resource
import com.apusart.cookit.tools.performRequest
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val apolloClient: ApolloClient) {
    suspend fun getAllRecipes(): Resource<RecipeListQuery.Data> {
        return performRequest(apolloClient, RecipeListQuery())
    }

    suspend fun getRecipeByName(name: String): Resource<RecipeListQuery.Data> {
        return performRequest(apolloClient, RecipeListQuery())
    }

    suspend fun getRecipes(from: Int, to: Int): Resource<RecipeScopedListQuery.Data> {
        return performRequest(apolloClient, RecipeScopedListQuery(from.toDouble(), to.toDouble()))
    }
}