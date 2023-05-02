package com.example.nfctron_app.spacexLaunches.launches

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.nfctron_app.R
import com.example.nfctron_app.databinding.ItemLaunchBinding
import com.example.nfctron_app.spacexLaunches.modelLaunches.LaunchesItem
import java.text.DecimalFormat

class LaunchesAdapter:
    ListAdapter<LaunchesItem, LaunchesAdapter.ItemViewHolder>(TransactionDiffCallback()) {
    inner class ItemViewHolder(private val binding: ItemLaunchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: LaunchesItem) {
            binding.textLaunchTitle.text = item.name
            binding.textCountdown.text = item.date_local.toString()
            if (item.links?.patch?.small != null) {
                binding.imageBadge.load(item.links.patch.small)
            } else {
                binding.imageBadge.load(R.drawable.baseline_rocket_launch_24)
            }
            binding.textLivestream.text = binding.root.context.getString(R.string.livestream)
            binding.textWiki.text = binding.root.context.getString(R.string.wiki)
        }
    }

    private class TransactionDiffCallback : DiffUtil.ItemCallback<LaunchesItem>() {
        override fun areItemsTheSame(oldItem: LaunchesItem, newItem: LaunchesItem): Boolean =
            oldItem.flight_number == newItem.flight_number

        override fun areContentsTheSame(oldItem: LaunchesItem, newItem: LaunchesItem): Boolean =
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