package com.example.nfctron_app.spacexLaunches.launches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.nfctron_app.R
import com.example.nfctron_app.databinding.FragmentLaunchesBinding
import com.example.nfctron_app.databinding.ItemLaunchBinding
import com.example.nfctron_app.spacexLaunches.databaseSpacexLaunches.SpacexLaunch
import com.example.nfctron_app.spacexLaunches.databaseSpacexLaunches.SpacexLaunchRepository
import com.example.nfctron_app.spacexLaunches.modelLaunches.LaunchesItem
import kotlinx.coroutines.launch

class LaunchesFragment : Fragment() {
    private var _binding: FragmentLaunchesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LaunchesViewModel>()
    private lateinit var launchesAdapter: LaunchesAdapter

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

        launchesAdapter = LaunchesAdapter(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            val pinnedLaunches = SpacexLaunchRepository.getAllSpacexLaunch()
            launchesAdapter.submitList(pinnedLaunches)

            binding.textUnpinAll.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    SpacexLaunchRepository.deleteAllSpacexLaunch()
                    launchesAdapter.submitList(pinnedLaunches)
                }
            }
        }

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
                    viewLifecycleOwner.lifecycleScope.launch {
                        val composeCarousel = view.findViewById<ComposeView>(R.id.composeCarousel)

                        composeCarousel.setContent {
                            val carouselItems = state.data
                            val columnWidthScreen = availableWidthDp().toInt()
                            CarouselView(items = carouselItems, columns = 2, columnWidth = columnWidthScreen) { item, columnWidth ->
                                val binding = ItemLaunchBinding.inflate(LayoutInflater.from(context), null, false)

                                binding.textLaunchTitle.text = item.name
                                binding.textCountdown.text = item.date_local.toString()
                                if (item.links?.patch?.small != null) {
                                    binding.imageBadge.load(item.links.patch.small)
                                } else {
                                    binding.imageBadge.load(R.drawable.baseline_rocket_launch_24)
                                }
                                binding.textLivestream.text = binding.root.context.getString(R.string.livestream)
                                binding.textWiki.text = binding.root.context.getString(R.string.wiki)

                                binding.cardIconImageView.setOnClickListener {
                                    val webcast = item.links?.webcast ?: ""
                                    val wikipedia = item.links?.wikipedia ?: ""
                                    val icon = item.links?.patch?.small ?: ""
                                    val name = item.name ?: ""
                                    val dateLocal = item.date_local?: ""
                                    val launchId = item.id ?: ""

                                    viewLifecycleOwner.lifecycleScope.launch {
                                        val existingItem = SpacexLaunchRepository.getSpacexLaunchById(launchId)
                                        if (existingItem == null) {
                                            SpacexLaunchRepository.insertSpacexLaunch(webcast, wikipedia, icon, name, dateLocal, launchId)
                                        } else {
                                            Toast.makeText(requireContext(), "Launch already pinned", Toast.LENGTH_SHORT).show()
                                        }
                                        val pinnedLaunches = SpacexLaunchRepository.getAllSpacexLaunch()
                                        launchesAdapter.submitList(pinnedLaunches)
                                    }
                                }
                                AndroidView(factory = { binding.root }, modifier = Modifier.width(columnWidth.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CarouselView(
    items: List<LaunchesItem>,
    columns: Int,
    columnWidth: Int,
    content: @Composable (LaunchesItem, Int) -> Unit
) {
    LazyHorizontalGrid(
        GridCells.Fixed(columns),
        contentPadding = PaddingValues(start = 48.dp)
    ) {
        items(count = items.size) { index ->
            val item = items[index]
            Row {
                Box(modifier = Modifier.width(columnWidth.dp)) {
                    content(item, columnWidth)
                }

                Spacer(modifier = Modifier.width(48.dp))
            }
        }
    }
}

@Composable
fun availableWidthDp(): Float {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.toFloat()

    val density = LocalDensity.current
    val twelveDpInPixels = with(density) { 28.dp.toPx() }

    val availableWidthMinus12dp = screenWidthDp - twelveDpInPixels

    return availableWidthMinus12dp
}
