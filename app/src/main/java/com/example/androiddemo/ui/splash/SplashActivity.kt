package com.example.androiddemo.ui.splash

import android.annotation.SuppressLint
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.androiddemo.databinding.ActivitySplashBinding
import com.example.common.ext.viewBinding
import com.example.common.helper.UiState
import com.example.common.router.navigate
import com.example.common.router.route.AccountOpeningRoute
import com.example.design.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private val viewModel by viewModels<SplashViewModel>()
    private val binding by viewBinding<ActivitySplashBinding>()

    override fun shouldCheckScreenIdle(): Boolean = false

    override fun onViewDidLoad() {
         viewModel.checkAppVersion()
    }

    override fun onViewConfiguration() {
        Thread.sleep(1000)
        installSplashScreen()
        setContentView(binding.root)
    }

    override fun onInitializeIntentData() {
        // do nothing
    }

    override fun onObserveState() {
        viewModel.appState.observe(this) { state->
            when(state) {
                is UiState.Loading -> {
                    //do nothing
                }
                is UiState.Success -> {
                    val isOutdated = state.data
                    if (isOutdated) {
                        showDialog(
                            title = "Your app is outdated!",
                            description = "Please download the latest application.",
                            positiveButtonText = "Update",
                            negativeButtonText = "Close",
                            positiveButtonCallback = {
                                //open playStore or huawei app gallery
                            },
                            negativeButtonCallback = {
                                finishAffinity()
                            }
                        )
                    }
                    else {
                        navigate(AccountOpeningRoute.LoginScreen).finish()
                    }
                }
                is UiState.Error -> {
                    navigate(AccountOpeningRoute.LoginScreen).finish()
                }
            }
        }
    }
}
