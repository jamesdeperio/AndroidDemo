package com.example.androiddemo.ui.main

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.androiddemo.R
import com.example.androiddemo.databinding.ActivityMainBinding
import com.example.common.ext.viewBinding
import com.example.design.base.BaseActivity

class MainActivity : BaseActivity() {

    private val binding by viewBinding<ActivityMainBinding>()
    override fun onViewDidLoad() {

    }

    override fun onViewConfiguration() {
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)

    }

    override fun onInitializeIntentData() {

    }

    override fun onObserveState() {

    }


}