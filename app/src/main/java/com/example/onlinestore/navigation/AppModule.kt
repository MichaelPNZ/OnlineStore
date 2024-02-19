package com.example.onlinestore.navigation

import android.content.Context
import com.example.core.navigation.DeeplinkHandler
import com.example.core.navigation.DeeplinkProcessor
import com.example.core.navigation.DefaultDeeplinkHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun providesDefaultDeeplinkHandler(
        processors: Set<@JvmSuppressWildcards DeeplinkProcessor>
    ): DeeplinkHandler = DefaultDeeplinkHandler(processors)

}