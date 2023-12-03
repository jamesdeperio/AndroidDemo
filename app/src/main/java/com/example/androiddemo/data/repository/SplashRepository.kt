package com.example.androiddemo.data.repository

import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class SplashRepository @Inject constructor(
    private val service: SplashService) {

    fun getVersionName(): Observable<String> {
        return Observable.fromCallable {
            "1.0.0"
        }
    }

}