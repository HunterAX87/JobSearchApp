package com.example.jobsearchapp.presentation.search

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.jobsearchapp.R
import com.example.jobsearchapp.data.model.Vacancy
import com.example.jobsearchapp.databinding.ItemVacancyBinding

class VacanciesAdapter(
    private var vacancies: List<Vacancy>,
    private val onClick: (Vacancy) -> Unit,
    private val onRespondClick: (Vacancy) -> Unit
) : RecyclerView.Adapter<VacanciesAdapter.VacancyViewHolder>() {

    class VacancyViewHolder(private val binding: ItemVacancyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            vacancy: Vacancy,
            clickListener: (Vacancy) -> Unit,
            respondClickListener: (Vacancy) -> Unit,
            resources: Resources
        ) {
            binding.vacancyTitle.text = vacancy.title
            binding.vacancyCompany.text = vacancy.company
            binding.vacancyLocation.text = vacancy.address.town
            binding.vacancyExperience.text = vacancy.experience.previewText
            binding.vacancyPublishedDate.text = formatPublishedDate(vacancy.publishedDate, resources)

            if (vacancy.salary.short.isNullOrEmpty()) {
                binding.vacancySalary.visibility = View.GONE
            } else {
                binding.vacancySalary.text = vacancy.salary.short
                binding.vacancySalary.visibility = View.VISIBLE
            }

            if (vacancy.lookingNumber > 0) {
                val tempLookingN= "Сейчас просматривает  ${getPersonDeclension(vacancy.lookingNumber,resources)}"
                binding.vacancyLookingNumber.text =tempLookingN

                binding.vacancyLookingNumber.visibility = View.VISIBLE
            } else {
                binding.vacancyLookingNumber.visibility = View.GONE
            }

            binding.vacancyFavoriteIcon.setImageResource(if (vacancy.isFavorite) R.drawable.favorites_true3x else R.drawable.favorites_false3x)

            binding.vacancyFavoriteIcon.setOnClickListener {
                vacancy.isFavorite = !vacancy.isFavorite
                binding.vacancyFavoriteIcon.setImageResource(if (vacancy.isFavorite) R.drawable.favorites_true3x else R.drawable.favorites_false3x) // Обновляем иконку
                clickListener(vacancy)
            }

            itemView.setOnClickListener {
                val bundle = Bundle().apply {
                    putParcelable("vacancy", vacancy)
                }
                val navController = Navigation.findNavController(itemView)
                navController.navigate(R.id.aboutFragment, bundle)
            }

            binding.bRespondd.setOnClickListener {
                respondClickListener(vacancy)
            }
        }

        private fun formatPublishedDate(date: String, resources: Resources): String {
            val parts = date.split("-")
            return "Опубликовано ${parts[2]} ${getMonthDeclension(parts[1].toInt(),resources)}"
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
        return VacancyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(
            vacancies[position],
            onClick,
            onRespondClick,
            holder.itemView.resources
        )
    }

    override fun getItemCount(): Int = vacancies.size

    fun updateVacancies(newVacancies: List<Vacancy>) {
        vacancies = newVacancies
        notifyDataSetChanged()
    }
}