package com.pluu.theme.sample.ui.first

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pluu.theme.sample.databinding.ActivityFirstBinding
import com.pluu.theme.sample.ui.base.ThemedActivity
import com.pluu.theme.sample.ui.second.SecondActivity

class FirstActivity : ThemedActivity() {

    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnGoSecond.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }
}