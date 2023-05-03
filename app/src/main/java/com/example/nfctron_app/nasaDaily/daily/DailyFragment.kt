package com.example.nfctron_app.nasaDaily.daily

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import com.example.nfctron_app.R
import com.example.nfctron_app.nasaDaily.databaseNasaDaily.NasaDailyRepository
import com.example.nfctron_app.databinding.FragmentDailyBinding
import kotlinx.coroutines.*

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
                    }
                    viewLifecycleOwner.lifecycleScope.launch {
                        val nasaDailyList = NasaDailyRepository.getNasaDaily()
                        with(binding) {
                            textTitle.text = nasaDailyList.title
                            textDateNumber.text = nasaDailyList.date
                            textDate.text = getString(R.string.today)
                            textSubheading.text = getString(R.string.explanation)
                            textExplanation.text = nasaDailyList.explanation

                            swipeRefreshDaily.isRefreshing = false
                            swipeRefreshDaily.setOnRefreshListener {
                                viewModel.retryLoadingData()
                            }
                        }

                    }
                }
                is DailyScreenState.Loading -> {
                    with(binding) {
                        progressDaily.visibility = View.VISIBLE
                    }
                }
                is DailyScreenState.Success -> {
                    with(binding) {
                        progressDaily.visibility = View.GONE
                        textTitle.text = state.data.title
                        textDateNumber.text = state.data.date
                        textDate.text = getString(R.string.today)
                        textSubheading.text = getString(R.string.explanation)
                        textExplanation.text = state.data.explanation

                        textTitle.setTextColor(Color.WHITE)
                        textDate.setTextColor(Color.WHITE)
                        textDateNumber.setTextColor(ContextCompat.getColor(binding.root.context, R.color.grey_text))


                        val context = requireContext()
                        viewLifecycleOwner.lifecycleScope.launch {
                            val lowQualityDrawable = context.imageLoader.execute(
                                ImageRequest.Builder(context)
                                    .data(state.data.url)
                                    .build()
                            ).drawable
                            image.load(state.data.hdurl) {
                                crossfade(true)
                                placeholder(lowQualityDrawable)
                                error(R.drawable.baseline_image_24)
                                listener(
                                    onSuccess = { _, _ ->
                                        // The high-quality image has been loaded successfully
                                    }
                                )
                            }
                        }

                        swipeRefreshDaily.isRefreshing = false
                        swipeRefreshDaily.setOnRefreshListener {
                            viewModel.retryLoadingData()
                        }
                    }

                    viewLifecycleOwner.lifecycleScope.launch {
                        if (state.data.date > (NasaDailyRepository.getNasaDaily()?.date ?: "")) {
                            NasaDailyRepository.deleteNasaDaily()
                            NasaDailyRepository.insertNasaDaily(
                                date = state.data.date,
                                explanation = state.data.explanation,
                                hdurl = state.data.hdurl,
                                title = state.data.title,
                                url = state.data.url
                            )
                        }
                    }
                }
            }
        }
    }
}