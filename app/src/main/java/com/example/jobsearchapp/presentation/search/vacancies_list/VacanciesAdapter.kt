package com.example.jobsearchapp.presentation.search.vacancies_list

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jobsearchapp.R
import com.example.jobsearchapp.databinding.ItemVacancyBinding

class VacanciesAdapter(
    private val onClick: (VacancyUI) -> Unit,
    private val onRespondClick: (VacancyUI) -> Unit
) : ListAdapter<VacancyUI, VacanciesAdapter.VacancyViewHolder>(VacancyDiffCallback()) {

    class VacancyViewHolder(
        private val binding: ItemVacancyBinding,
        private val onClick: (VacancyUI) -> Unit,
        private val onRespondClick: (VacancyUI) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            vacancy: VacancyUI
        ) {
            binding.vacancyTitle.text = vacancy.title
            binding.vacancyCompany.text = vacancy.company
            binding.vacancyLocation.text = vacancy.address.town
            binding.vacancyExperience.text = vacancy.experience.previewText
            binding.vacancyPublishedDate.text =
                formatPublishedDate(vacancy.publishedDate, binding.root.context.resources)

            if (vacancy.salary.short.isNullOrEmpty()) {
                binding.vacancySalary.visibility = View.GONE
            } else {
                binding.vacancySalary.text = vacancy.salary.short
                binding.vacancySalary.visibility = View.VISIBLE
            }

            if (vacancy.lookingNumber > 0) {
                val tempLookingN = "Сейчас просматривает  ${
                    getPersonDeclension(
                        vacancy.lookingNumber,
                        binding.root.context.resources
                    )
                }"
                binding.vacancyLookingNumber.text = tempLookingN

                binding.vacancyLookingNumber.visibility = View.VISIBLE
            } else {
                binding.vacancyLookingNumber.visibility = View.GONE
            }

            binding.vacancyFavoriteIcon.setImageResource(if (vacancy.isFavorite) R.drawable.favorites_true3x else R.drawable.favorites_false3x)
            binding.vacancyFavoriteIcon.setOnClickListener {
                vacancy.isFavorite = !vacancy.isFavorite
                binding.vacancyFavoriteIcon.setImageResource(if (vacancy.isFavorite) R.drawable.favorites_true3x else R.drawable.favorites_false3x) // Обновляем иконку
                onClick(vacancy)
            }

            itemView.setOnClickListener {
                val bundle = Bundle().apply {
                    putSerializable("vacancy", vacancy)
                }
                val navController = Navigation.findNavController(itemView)
                navController.navigate(R.id.aboutFragment, bundle)
            }

            binding.bRespondd.setOnClickListener {
                onRespondClick(vacancy)
            }
        }

        private fun formatPublishedDate(date: String, resources: Resources): String {
            val parts = date.split("-")
            return "Опубликовано ${parts[2]} ${getMonthDeclension(parts[1].toInt(), resources)}"
        }

        private fun getMonthDeclension(month: Int, resources: Resources): String {
            val monthsArray = resources.getStringArray(R.array.months)
            return if (month in 1..12) {
                monthsArray[month - 1]
            } else {
                ""
            }
        }

        private fun getPersonDeclension(number: Int, resources: Resources): String {
            return resources.getQuantityString(
                R.plurals.person_count,
                number,
                number
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val binding = ItemVacancyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VacancyViewHolder(binding, onClick, onRespondClick)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(
            getItem(position)
        )
    }
}

class VacancyDiffCallback : DiffUtil.ItemCallback<VacancyUI>() {
    override fun areItemsTheSame(oldItem: VacancyUI, newItem: VacancyUI): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: VacancyUI, newItem: VacancyUI): Boolean {
        return oldItem == newItem
    }
}