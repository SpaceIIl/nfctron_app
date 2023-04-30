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

        val launchesAdapter = LaunchesAdapter()

        binding.recyclerPinned.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = launchesAdapter
            Log.d("fragment1", "$layoutManager")
            Log.d("fragment2", "$adapter")
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
//                        progressPool.visibility = View.GONE
//                        retryButton.visibility = View.GONE
//                        recyclerMyData.visibility = View.VISIBLE
//                        textLatestTransactions.text = getString(R.string.latest_transactions)
                        swipeRefreshLaunches.isRefreshing = false
                        swipeRefreshLaunches.setOnRefreshListener {
                            viewModel.retryLoadingData()
                        }
                    }

                    Log.d("fragment3", "" + state.data)
                    launchesAdapter.submitList(state.data)
                    binding.recyclerPinned.post {
                        binding.recyclerPinned.layoutManager?.scrollToPosition(0)
                    }
                }
            }
        }
    }
}