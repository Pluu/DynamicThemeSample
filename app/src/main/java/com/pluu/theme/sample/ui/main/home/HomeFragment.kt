package com.pluu.theme.sample.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pluu.theme.sample.databinding.FragmentHomeBinding
import com.pluu.theme.sample.ui.main.MainFragmentProvider
import com.pluu.theme.sample.utils.showToast

class HomeFragment : Fragment(), MainFragmentProvider {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRestart() {
        showToast("Restart Home")
    }

    override fun onReselected() {
        showToast("Reselected Home")
    }
}