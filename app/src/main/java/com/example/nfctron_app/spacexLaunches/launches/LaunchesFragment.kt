package com.example.nfctron_app.spacexLaunches.launches

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nfctron_app.R
import com.example.nfctron_app.databinding.FragmentLaunchesBinding
import com.example.nfctron_app.databinding.ItemLaunchBinding

class LaunchesFragment : Fragment() {
    private var _binding: FragmentLaunchesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LaunchesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaunchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textPinned.text = getString(R.string.pinned)
        binding.textUnpinAll.text = getString(R.string.unpin_all)
        binding.textUpcoming.text = getString(R.string.upcoming)
        binding.textSortBy.text = getString(R.string.sort_by)

        val launchesAdapter = LaunchesAdapter()

        binding.recyclerPinned.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = launchesAdapter
        }

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LaunchesScreenState.Error -> {
                    with(binding) {
                        progressLaunches.visibility = View.GONE
                        swipeRefreshLaunches.setOnClickListener {
                            viewModel.retryLoadingData()
                        }
                    }
                }
                is LaunchesScreenState.Loading -> {
                    with(binding) {
                        progressLaunches.visibility = View.VISIBLE
                    }
                }
                is LaunchesScreenState.Success -> {
                    with(binding) {
                        progressLaunches.visibility = View.GONE
                        swipeRefreshLaunches.isRefreshing = false
                        swipeRefreshLaunches.setOnRefreshListener {
                            viewModel.retryLoadingData()
                        }
                    }

                    launchesAdapter.submitList(state.data)
                    binding.recyclerPinned.post {
                        binding.recyclerPinned.layoutManager?.scrollToPosition(0)
                    }
                }
            }
        }
    }
}