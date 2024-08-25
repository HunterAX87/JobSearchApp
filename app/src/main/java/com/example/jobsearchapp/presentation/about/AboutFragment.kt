package com.example.jobsearchapp.presentation.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.jobsearchapp.R
import com.example.jobsearchapp.data.Vacancy
import com.example.jobsearchapp.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получаем данные о вакансии из аргументов
        val vacancy: Vacancy? = arguments?.getParcelable("vacancy")

        vacancy?.let { vac -> // Используем vac вместо it
            // Заполнение данных в UI
            binding.tvTitle.text = vac.title
            binding.tvSalary.text = vac.salary.full ?: "Уровень дохода не указан"
            binding.tvExperience.text = vac.experience.previewText
            binding.tvSchedules.text = vac.schedules.joinToString(", ") { schedule -> schedule.capitalize() }

            // Отображение количества откликов
            vac.appliedNumber.takeIf { it > 0 }?.let { appliedNumber ->
                binding.tvAppliedNumber.text = "$appliedNumber человек уже откликнулось"
            }

            // Отображение количества просмотров
            vac.lookingNumber.takeIf { it > 0 }?.let { lookingNumber ->
                binding.tvLookingNumber.text = "$lookingNumber человека сейчас смотрят"
            }

            binding.tvComp.text = vac.company

            // Заполнение адреса
            binding.tvAdress.text = "${vac.address.town}, ${vac.address.street}, ${vac.address.house}"

            // Заполнение описания
            binding.tvDescription.text = vac.description ?: ""

            // Заполнение обязанностей
            binding.tvResponsibilities.text = vac.responsibilities?.replace("\n", "\n") ?: ""

            // Заполнение вопросов
            binding.questionsContainer.removeAllViews()
            vac.questions.forEach { question ->
                val questionTextView = TextView(context).apply {
                    text = question
                    setOnClickListener {
                        // Логика для отображения модального окна с вопросом
                    }
                }
                binding.questionsContainer.addView(questionTextView)
            }

            // Обработка избранного
            updateFavoriteIcon(vac.isFavorite) // Используем vac вместо it
            binding.imFavorite.setOnClickListener {
                vac.isFavorite = !vac.isFavorite // Используем vac вместо it
                updateFavoriteIcon(vac.isFavorite)
                // Сохранение состояния в БД
                // viewModel.saveOrUpdateVacancy(vac) // Убедитесь, что viewModel определен
            }
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        binding.imFavorite.setImageResource(if (isFavorite) R.drawable.favorites_true3x else R.drawable.favorites_false3x)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}