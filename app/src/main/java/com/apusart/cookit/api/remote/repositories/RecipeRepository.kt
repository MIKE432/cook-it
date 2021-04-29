package com.apusart.cookit.api.remote.repositories

import com.apollographql.apollo.ApolloClient
import com.apusart.cookit.RecipeListQuery
import com.apusart.cookit.api.Resource
import com.apusart.cookit.tools.performRequest
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val apolloClient: ApolloClient) {
    suspend fun getAllRecipes(): Resource<RecipeListQuery.Data> {
        return performRequest(apolloClient, RecipeListQuery::class.java)
    }
}