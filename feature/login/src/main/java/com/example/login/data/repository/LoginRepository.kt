package com.example.login.data.repository

import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class LoginRepository @Inject constructor(private val service: LoginService) {
    fun getVersionName(): Observable<String> {
        return Observable.fromCallable {
            "2.0.0"
        }
    }

}