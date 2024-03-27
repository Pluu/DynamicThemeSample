package com.pluu.theme.sample.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pluu.theme.sample.R
import com.pluu.theme.sample.databinding.ActivityMainBinding
import com.pluu.theme.sample.utils.ThemeHelper
import com.pluu.theme.sample.utils.ThemeProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ThemeProvider {

    @StyleRes
    override var themeId: Int = 0

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeHelper.setTheme(this, viewModel.currentTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.theme.collect { theme ->
                        ThemeHelper.setCorrectTheme(this@MainActivity, theme)
                    }
                }
            }
        }
    }

    override fun setTheme(resId: Int) {
        super.setTheme(resId)
        this.themeId = resId
    }
}