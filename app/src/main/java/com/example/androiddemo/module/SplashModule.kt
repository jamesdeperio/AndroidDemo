package com.example.androiddemo.module

import com.example.androiddemo.data.interactor.SplashUseCase
import com.example.androiddemo.data.repository.SplashRepository
import com.example.androiddemo.data.repository.SplashService
import com.example.common.network.NetworkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SplashAPIModule {
    @Provides
    @Singleton
    fun provideService(networkManager: NetworkManager) : SplashService =
        networkManager.create(SplashService::class.java) as SplashService

}

@Module
@InstallIn(ActivityComponent::class)
object SplashModule {

    @Provides
    @ActivityScoped
    fun provideRepository(service: SplashService) : SplashRepository =
        SplashRepository(service)

    @Provides
    @ActivityScoped
    fun provideUseCase(repository: SplashRepository) : SplashUseCase =
        SplashUseCase(repository)

}
