package com.example.jobsearchapp.presentation.search.ofers_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jobsearchapp.data.model.Offer
import com.example.jobsearchapp.databinding.ItemOfferBinding
import com.example.jobsearchapp.presentation.search.mapToUI

class OffersAdapter : ListAdapter<Offer, OffersAdapter.OfferViewHolder>(OfferDiffCallback()) {

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
        holder.bind(offer.mapToUI(), onOfferClick)
    }
}

class OfferDiffCallback : DiffUtil.ItemCallback<Offer>() {
    override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
        return oldItem == newItem
    }
}