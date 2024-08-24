package com.example.jobsearchapp.presentation.search.vacancies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobsearchapp.R
import com.example.jobsearchapp.data.Vacancy

class VacanciesAdapter(
    private var vacancies: List<Vacancy>,
    private val onClick: (Vacancy) -> Unit
) : RecyclerView.Adapter<VacanciesAdapter.VacancyViewHolder>() {

    class VacancyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.vacancy_title)
        val company: TextView = view.findViewById(R.id.vacancy_company)
        val location: TextView = view.findViewById(R.id.vacancy_location)
        val experience: TextView = view.findViewById(R.id.vacancy_experience)
        val publishedDate: TextView = view.findViewById(R.id.vacancy_published_date)
        val lookingNumber: TextView = view.findViewById(R.id.vacancy_looking_number)
        val favoriteIcon: ImageView = view.findViewById(R.id.vacancy_favorite_icon)

        fun bind(vacancy: Vacancy, clickListener: (Vacancy) -> Unit) {
            title.text = vacancy.title
            company.text = vacancy.company
            location.text = vacancy.address.town
            experience.text = vacancy.experience.previewText
            publishedDate.text = formatPublishedDate(vacancy.publishedDate)

            // Установка количества просматривающих
            if (vacancy.lookingNumber > 0) {
                lookingNumber.text = "Сейчас просматривает ${vacancy.lookingNumber} ${getPersonDeclension(vacancy.lookingNumber)}"
                lookingNumber.visibility = View.VISIBLE
            } else {
                lookingNumber.visibility = View.GONE
            }

            // Установка иконки избранного
            favoriteIcon.setImageResource(if (vacancy.isFavorite) R.drawable.icon_favorite_filled else R.drawable.icon_favorite_empty)

            itemView.setOnClickListener {
                clickListener(vacancy) // Обработка клика по вакансии
            }
        }

        private fun formatPublishedDate(date: String): String {
            // Преобразование даты в нужный формат
            val parts = date.split("-")
            return "Опубликовано ${parts[2]} ${getMonthDeclension(parts[1].toInt())}"
        }

        private fun getMonthDeclension(month: Int): String {
            return when (month) {
                1 -> "января"
                2 -> "февраля"
                3 -> "марта"
                4 -> "апреля"
                5 -> "мая"
                6 -> "июня"
                7 -> "июля"
                8 -> "августа"
                9 -> "сентября"
                10 -> "октября"
                11 -> "ноября"
                12 -> "декабря"
                else -> ""
            }
        }

        private fun getPersonDeclension(number: Int): String {
            return when {
                number % 10 == 1 && number % 100 != 11 -> "человек"
                number % 10 in 2..4 && (number % 100 < 10 || number % 100 > 20) -> "человека"
                else -> "человек"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vacancy, parent, false)
        return VacancyViewHolder(view)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancies[position], onClick)
    }

    override fun getItemCount(): Int = vacancies.size

    fun updateVacancies(newVacancies: List<Vacancy>) {
        vacancies = newVacancies
        notifyDataSetChanged()
    }
}