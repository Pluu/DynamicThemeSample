package com.pluu.theme.sample.ui.setting

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.pluu.theme.sample.databinding.ActivitySettingBinding
import com.pluu.theme.sample.model.Theme
import com.pluu.theme.sample.ui.base.ThemedActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : ThemedActivity() {

    private lateinit var binding: ActivitySettingBinding
    private val viewModel by viewModels<SettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.onlyCustomTheme.setOnClickListener {
            showUseCustomTheme()
        }
        binding.changeTheme.setOnClickListener {
            showSelectTheme()
        }
    }

    private fun showUseCustomTheme() {
        AlertDialog.Builder(this)
            .setSingleChoiceItems(
                arrayOf("Used custom theme", "Default"),
                if (viewModel.isUsedCustomTheme) 0 else 1,
            ) { dialogInterface, item ->
                viewModel.setUseCustomTheme(if (item == 0) true else false)
                dialogInterface.dismiss()
            }.show()
    }

    private fun showSelectTheme() {
        AlertDialog.Builder(this)
            .setSingleChoiceItems(
                Theme.entries.map { it.name }.toTypedArray(),
                currentTheme.ordinal,
            ) { dialogInterface, item ->
                viewModel.setTheme(Theme.of(item))
                dialogInterface.dismiss()
            }.show()
    }
}