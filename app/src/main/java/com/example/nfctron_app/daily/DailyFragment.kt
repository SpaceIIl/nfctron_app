package com.example.nfctron_app.daily

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.nfctron_app.database.AppDatabase
import com.example.nfctron_app.database.NasaDaily
import com.example.nfctron_app.database.NasaDailyRepository
import com.example.nfctron_app.databinding.FragmentDailyBinding
import kotlinx.coroutines.launch

class DailyFragment : Fragment() {
    private var _binding: FragmentDailyBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DailyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when(state){
                is DailyScreenState.Error -> {
                    with(binding) {
                        progressDaily.visibility = View.GONE
                        retryButton.visibility = View.VISIBLE
                        retryButton.setOnClickListener {
                            viewModel.retryLoadingData()
                        }
                    }
                    viewLifecycleOwner.lifecycleScope.launch {
                        val nasaDailyList = NasaDailyRepository.getNasaDaily()
                        binding.textDate.text = nasaDailyList.toString()
                        binding.textImg.text = nasaDailyList.toString()
                    }
                }
                is DailyScreenState.Loading -> {
                    with(binding) {
                        progressDaily.visibility = View.VISIBLE
                        retryButton.visibility = View.GONE
                    }
                }
                is DailyScreenState.Success -> {
                    with(binding) {
                        progressDaily.visibility = View.GONE
                        retryButton.visibility = View.GONE
                    }
                    //bindPoolWrapper(state.data)
                    binding.textDate.text = state.data.date
                    binding.textImg.text = state.data.id.toString()
                }
            }
        }
    }
}