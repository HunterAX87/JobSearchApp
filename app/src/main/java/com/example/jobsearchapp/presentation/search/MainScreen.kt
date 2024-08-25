package com.example.jobsearchapp.presentation.search

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobsearchapp.MyApplication
import com.example.jobsearchapp.R
import com.example.jobsearchapp.data.Vacancy
import com.example.jobsearchapp.databinding.FragmentMainScreenBinding
import com.example.jobsearchapp.presentation.search.vacancies.VacanciesAdapter
import javax.inject.Inject

class MainScreen : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MainViewModel by viewModels { viewModelFactory }
    private lateinit var offersAdapter: OffersAdapter
    private lateinit var vacanciesAdapter: VacanciesAdapter
    private var allVacancies: List<Vacancy> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Внедрение зависимостей Dagger
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация адаптеров
        offersAdapter = OffersAdapter(emptyList()) { link ->
            // Открытие ссылки в браузере
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }


        // Создание адаптера вакансий с обработчиком клика
        vacanciesAdapter = VacanciesAdapter(emptyList()) { vacancy ->
            // Сохранение или обновление вакансии в базе данных

            viewModel.saveOrUpdateVacancy(vacancy)
        }

        binding.rcViewRec.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcViewVacancies.layoutManager = LinearLayoutManager(requireContext())

        // Установка адаптеров
        binding.rcViewRec.adapter = offersAdapter
        binding.rcViewVacancies.adapter = vacanciesAdapter

        // Наблюдение за изменениями в offers
        viewModel.offers.observe(viewLifecycleOwner) { offers ->
            if (offers.isNotEmpty()) {
                offersAdapter.updateOffers(offers)
                binding.rcViewRec.visibility = View.VISIBLE
            } else {
                binding.rcViewRec.visibility = View.GONE
            }
        }

        // Наблюдение за изменениями в vacancies
        viewModel.vacancies.observe(viewLifecycleOwner) { vacancies ->
            allVacancies = vacancies
            val initialVacancies = vacancies.take(3) // Берем первые 3 вакансии
            vacanciesAdapter.updateVacancies(initialVacancies)

            binding.bMore.text = "Еще ${vacancies.size} ${getVacancyDeclension(vacancies.size)}"
            binding.bMore.visibility = if (vacancies.size > 3) View.VISIBLE else View.GONE
        }

        // Обработка клика на кнопку "Еще"
        binding.bMore.setOnClickListener {
            vacanciesAdapter.updateVacancies(allVacancies) // Показываем все вакансии
            binding.bMore.visibility = View.GONE // Скрываем кнопку
            binding.rcViewRec.visibility = View.GONE // Скрываем rcViewRec
            binding.linearMore.visibility = View.VISIBLE // Показываем linearMore
            binding.tvVacanciesFY.visibility = View.GONE // Скрываем tvVacanciesFY

            // Показываем кнопку imBack
            binding.imBack.visibility = View.VISIBLE

            // Обновляем текст vacanciesNum
            binding.vacanciesNum.text = "${allVacancies.size} ${getVacancyDeclension(allVacancies.size)}"
        }

        // Обработка клика на кнопку "Назад"
        binding.imBack.setOnClickListener {
            // Возвращаемся к первому состоянию
            binding.rcViewRec.visibility = View.VISIBLE // Показываем rcViewRec
            binding.linearMore.visibility = View.GONE // Скрываем linearMore
            binding.tvVacanciesFY.visibility = View.VISIBLE // Показываем tvVacanciesFY
            binding.imBack.visibility = View.GONE // Скрываем imBack
        }

        // Загрузка данных
        viewModel.loadOffers()
        viewModel.loadVacancies() // Метод для загрузки вакансий
    }

    private fun getVacancyDeclension(count: Int): String {
        return when {
            count % 10 == 1 && count % 100 != 11 -> "вакансия"
            count % 10 in 2..4 && (count % 100 < 10 || count % 100 > 20) -> "вакансии"
            else -> "вакансий"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Освобождаем binding, чтобы избежать утечек памяти
    }
}