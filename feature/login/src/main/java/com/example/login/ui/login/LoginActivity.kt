package com.example.login.ui.login

import androidx.activity.viewModels
import com.example.common.ext.viewBinding
import com.example.common.router.navigate
import com.example.common.router.route.MainRoute
import com.example.design.base.BaseActivity
import com.example.design.listener.setOnThrottleClickListener
import com.example.login.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private val viewModel by viewModels<LoginViewModel>()
    private val binding by viewBinding<ActivityLoginBinding>()

    override fun shouldCheckScreenIdle(): Boolean = false

    override fun onViewDidLoad() {

    }

    override fun onViewConfiguration() {
        setContentView(binding.root)
        binding.root.clearFocus()
        binding.btnLogin.setOnThrottleClickListener {
            navigate(MainRoute.MainScreen)
        }
    }


    override fun onInitializeIntentData() {

    }

    override fun onObserveState() {

    }

}