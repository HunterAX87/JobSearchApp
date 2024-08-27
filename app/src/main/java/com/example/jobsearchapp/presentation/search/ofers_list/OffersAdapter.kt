package com.example.jobsearchapp.presentation.search.ofers_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jobsearchapp.databinding.ItemOfferBinding

class OffersAdapter : ListAdapter<OfferUI, OffersAdapter.OfferViewHolder>(OfferDiffCallback()) {

    private var onOfferClick: ((String) -> Unit)? = null

    fun setOnOfferClickListener(listener: (String) -> Unit) {
        onOfferClick = listener
    }

    class OfferViewHolder(private val binding: ItemOfferBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(offerUI: OfferUI, onOfferClick: ((String) -> Unit)?) {
            binding.offerTitle.text = offerUI.title
            binding.offerIcon.setImageResource(offerUI.iconRes)

            itemView.setOnClickListener {
                onOfferClick?.invoke(offerUI.link)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val binding = ItemOfferBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfferViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = getItem(position)
        holder.bind(offer, onOfferClick)
    }
}

class OfferDiffCallback : DiffUtil.ItemCallback<OfferUI>() {
    override fun areItemsTheSame(oldItem: OfferUI, newItem: OfferUI): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OfferUI, newItem: OfferUI): Boolean {
        return oldItem == newItem
    }
}