package com.example.login.module

import com.example.common.network.NetworkManager
import com.example.login.data.interactor.LoginUseCase
import com.example.login.data.repository.LoginRepository
import com.example.login.data.repository.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginAPIModule {
    @Provides
    @Singleton
    fun provideService(networkManager: NetworkManager) : LoginService =
        networkManager.create(LoginService::class.java) as LoginService

}

@Module
@InstallIn(ActivityComponent::class)
object LoginModule {


    @Provides
    @ActivityScoped
    fun provideRepository(service: LoginService) : LoginRepository =
        LoginRepository(service)

    @Provides
    @ActivityScoped
    fun provideUseCase(repository: LoginRepository) : LoginUseCase =
        LoginUseCase(repository)

}
