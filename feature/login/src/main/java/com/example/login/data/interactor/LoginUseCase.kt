package com.example.login.data.interactor

import com.example.login.data.repository.LoginRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: LoginRepository) {

    fun getVersionName(): Observable<String> {
        return repository.getVersionName()

    }
}