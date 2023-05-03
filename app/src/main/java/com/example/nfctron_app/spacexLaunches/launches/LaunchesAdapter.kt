package com.example.nfctron_app.spacexLaunches.launches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.nfctron_app.R
import com.example.nfctron_app.databinding.ItemLaunchBinding
import com.example.nfctron_app.spacexLaunches.databaseSpacexLaunches.SpacexLaunch
import com.example.nfctron_app.spacexLaunches.modelLaunches.LaunchesItem

class LaunchesAdapter(private val launches: List<SpacexLaunch>) :
    ListAdapter<SpacexLaunch, LaunchesAdapter.ItemViewHolder>(TransactionDiffCallback()) {
    inner class ItemViewHolder(private val binding: ItemLaunchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: SpacexLaunch) {
            binding.textLaunchTitle.text = item.name
            binding.textCountdown.text = item.dateLocal
            if (item.icon != null) {
                binding.imageBadge.load(item.icon)
            } else {
                binding.imageBadge.load(R.drawable.baseline_rocket_launch_24)
            }
            binding.textLivestream.text = binding.root.context.getString(R.string.livestream)
            binding.textWiki.text = binding.root.context.getString(R.string.wiki)
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