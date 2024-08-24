package com.example.jobsearchapp.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobsearchapp.R
import com.example.jobsearchapp.data.Offer

class OffersAdapter(
    private var offers: List<Offer>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<OffersAdapter.OfferViewHolder>() {

    class OfferViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.offer_title)
        val icon: ImageView = view.findViewById(R.id.offer_icon)

        fun bind(offer: Offer, clickListener: (String) -> Unit) {
            title.text = offer.title
            icon.setImageResource(getIconResource(offer.id)) // Установка иконки в зависимости от id

            itemView.setOnClickListener {
                clickListener(offer.link) // Обработка клика
            }
        }

        private fun getIconResource(offerId: String?): Int {
            return when (offerId) {
                "near_vacancies" -> R.drawable.geo100 // Укажите свой ресурс
                "level_up_resume" -> R.drawable.star2x // Укажите свой ресурс
                "temporary_job" -> R.drawable.temp_job2x // Укажите свой ресурс
                else -> 0 // Иконка по умолчанию или 0 для скрытия
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_offer, parent, false)
        return OfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.bind(offers[position], onClick)
    }

    override fun getItemCount(): Int = offers.size

    fun updateOffers(newOffers: List<Offer>) {
        offers = newOffers
        notifyDataSetChanged()
    }
}