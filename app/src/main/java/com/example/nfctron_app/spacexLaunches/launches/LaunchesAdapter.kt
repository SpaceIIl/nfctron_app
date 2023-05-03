package com.example.nfctron_app.spacexLaunches.launches

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.nfctron_app.ConnectivityUtils.isNetworkConnected
import com.example.nfctron_app.R
import com.example.nfctron_app.databinding.ItemLaunchBinding
import com.example.nfctron_app.spacexLaunches.databaseSpacexLaunches.SpacexLaunch
import com.example.nfctron_app.spacexLaunches.databaseSpacexLaunches.SpacexLaunchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class LaunchesAdapter(private val lifecycleScope: CoroutineScope) :
    ListAdapter<SpacexLaunch, LaunchesAdapter.ItemViewHolder>(TransactionDiffCallback()) {

    inner class ItemViewHolder(private val binding: ItemLaunchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: SpacexLaunch) {
            val isConnected = binding.root.context.isNetworkConnected()
            binding.textLaunchTitle.text = item.name
            binding.textCountdown.text = item.dateLocal
            if (isConnected && item.icon != null && "http" in item.icon) {
                binding.imageBadge.load(item.icon)
            } else {
                binding.imageBadge.load(R.drawable.baseline_rocket_launch_24)
            }
            binding.textLivestream.text = binding.root.context.getString(R.string.livestream)
            binding.textWiki.text = binding.root.context.getString(R.string.wiki)
            binding.cardIconImageView.setBackgroundResource(R.drawable.rounded_corner_background)

            binding.cardIconImageView.setOnClickListener {
                lifecycleScope.launch {
                    SpacexLaunchRepository.deleteSpacexLaunch(item.launchId)
                    val pinnedLaunches = SpacexLaunchRepository.getAllSpacexLaunch()
                    submitList(pinnedLaunches)
                }
            }
        }
    }

    private class TransactionDiffCallback : DiffUtil.ItemCallback<SpacexLaunch>() {
        override fun areItemsTheSame(oldItem: SpacexLaunch, newItem: SpacexLaunch): Boolean =
            oldItem.launchId == newItem.launchId

        override fun areContentsTheSame(oldItem: SpacexLaunch, newItem: SpacexLaunch): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = ItemLaunchBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}