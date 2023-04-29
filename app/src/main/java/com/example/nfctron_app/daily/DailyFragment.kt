package com.example.nfctron_app.daily

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import com.example.nfctron_app.R
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
            when (state) {
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
                        binding.textDate.text = nasaDailyList.date
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
                    binding.textTitle.text = state.data.title
                    binding.textDateNumber.text = state.data.date
                    binding.textDate.text = getString(R.string.today)
                    binding.textSubheading.text = getString(R.string.explanation)
                    binding.textExplanation.text = state.data.explanation
                    binding.image.load(state.data.url)
                    binding.image.load(state.data.hdurl)

//                    context?.let { context ->
//                        val request = ImageRequest.Builder(context)
//                            .data(state.data.url)
//                            .target(onSuccess = { result ->
//                                binding.image.setImageDrawable(result)
//                                binding.image.postDelayed({
//                                    val hdRequest = ImageRequest.Builder(context)
//                                        .data(state.data.hdurl)
//                                        .target(onSuccess = { hdResult ->
//                                            binding.image.setImageDrawable(hdResult)
//                                        })
//                                        .build()
//                                    context.imageLoader.enqueue(hdRequest)
//                                }, 1000)
//                            })
//                            .build()
//                        context.imageLoader.enqueue(request)
//                    }
                }
            }
        }
    }
}