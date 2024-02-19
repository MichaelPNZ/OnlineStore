package com.example.onlinestore.navigation

import com.example.core.navigation.DeeplinkProcessor
import com.example.login.LoginDeeplinkProcessor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
interface DeepLinkProcessorModule {

    @Binds
    @IntoSet
    fun bindFeat01Processors(
        feature01DeeplinkProcessor: LoginDeeplinkProcessor
    ): DeeplinkProcessor

}