package com.example.login.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.common.helper.UiState
import com.example.design.base.BaseViewModel
import com.example.login.data.interactor.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel  @Inject constructor(
    private val splashUseCase: LoginUseCase): BaseViewModel() {

    private val _version = MutableLiveData<UiState<Boolean>>()
    val version: LiveData<UiState<Boolean>>
        get() = _version



}

