package com.example.androiddemo.data.interactor

import com.example.androiddemo.data.repository.SplashRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class SplashUseCase @Inject constructor(private val repository: SplashRepository) {
    fun getVersionName(): Observable<String> {
        return repository.getVersionName()

    }
}