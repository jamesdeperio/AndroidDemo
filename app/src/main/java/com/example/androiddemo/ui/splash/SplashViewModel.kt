package com.example.androiddemo.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androiddemo.BuildConfig
import com.example.androiddemo.data.interactor.SplashUseCase
import com.example.common.helper.UiState
import com.example.design.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import org.apache.maven.artifact.versioning.DefaultArtifactVersion
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val splashUseCase: SplashUseCase): BaseViewModel() {

    private val _appState = MutableLiveData<UiState<Boolean>>()
    val appState: LiveData<UiState<Boolean>>
        get() = _appState

    fun checkAppVersion() {
       splashUseCase.getVersionName()
           .delay(1,TimeUnit.SECONDS)
           .observeOn(AndroidSchedulers.mainThread())
           .doOnSubscribe {
                _appState.value = UiState.Loading
            }
            .subscribe({
                val fromAPI = DefaultArtifactVersion(it.substringBefore("-"))
                val fromBuild = DefaultArtifactVersion(BuildConfig.VERSION_NAME)
                _appState.value = UiState.Success(fromAPI > fromBuild)
            },{
                _appState.value = UiState.Error(it)
            }).addTo(disposable)
    }

}

