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
import com.example.nfctron_app.databinding.ItemLaunchBinding
import com.example.nfctron_app.spacexLaunches.modelLaunches.LaunchesItem
import java.text.DecimalFormat

class LaunchesAdapter:
    ListAdapter<LaunchesItem, LaunchesAdapter.ItemViewHolder>(TransactionDiffCallback()) {
    inner class ItemViewHolder(private val binding: ItemLaunchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: LaunchesItem) {
            binding.textLaunchTitle.text = item.name
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