package com.apusart.cookit.di

import com.apollographql.apollo.ApolloClient
import com.apusart.cookit.tools.Consts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModules {

    @Provides
    @Singleton
    fun provideApollo(): ApolloClient =
        ApolloClient
            .builder()
            .serverUrl(Consts.SERVER_URL)
            .build()
}