package com.pluu.theme.sample.ui.main.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pluu.theme.sample.databinding.FragmentDashboardBinding
import com.pluu.theme.sample.ui.main.MainFragmentProvider
import com.pluu.theme.sample.utils.showToast

class DashboardFragment : Fragment(), MainFragmentProvider {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DashboardViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.text.observe(viewLifecycleOwner) { text ->
            binding.textDashboard.text = text
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRestart() {
        showToast("Restart Dashboard")
    }

    override fun onReselected() {
        showToast("Reselected Dashboard")
        binding.scrollView.smoothScrollTo(0, 0)
    }
}