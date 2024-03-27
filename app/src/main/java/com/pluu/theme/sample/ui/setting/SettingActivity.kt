package com.pluu.theme.sample.ui.setting

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pluu.theme.sample.databinding.ActivitySettingBinding
import com.pluu.theme.sample.model.Theme
import com.pluu.theme.sample.utils.ThemeHelper
import com.pluu.theme.sample.utils.ThemeProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingActivity : AppCompatActivity(), ThemeProvider {

    @StyleRes
    override var themeId: Int = 0

    private lateinit var binding: ActivitySettingBinding
    private val viewModel by viewModels<SettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeHelper.setTheme(this, viewModel.currentTheme)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            showSelectTheme()
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.theme.collect { theme ->
                        ThemeHelper.setCorrectTheme(this@SettingActivity, theme)
                    }
                }
            }
        }
    }

    private fun showSelectTheme() {
        AlertDialog.Builder(this)
            .setSingleChoiceItems(
                Theme.entries.map { it.name }.toTypedArray(),
                viewModel.currentTheme.ordinal,
            ) { dialogInterface, item ->
                viewModel.setTheme(Theme.of(item))
                dialogInterface.dismiss()
            }.show()
    }

    override fun setTheme(resId: Int) {
        super.setTheme(resId)
        this.themeId = resId
    }

}