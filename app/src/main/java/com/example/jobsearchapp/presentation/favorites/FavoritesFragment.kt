package com.example.jobsearchapp.presentation.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobsearchapp.MyApplication
import com.example.jobsearchapp.databinding.FragmentFavoritesBinding
import com.example.jobsearchapp.presentation.search.MainViewModel
import com.example.jobsearchapp.presentation.search.ViewModelFactory
import com.example.jobsearchapp.presentation.search.vacancies.VacanciesAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MainViewModel by viewModels { viewModelFactory }
    private lateinit var favoritesAdapter: VacanciesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Внедрение зависимостей Dagger
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация адаптера
        favoritesAdapter = VacanciesAdapter(emptyList()) { vacancy ->
            // Удаление вакансии из избранного
            vacancy.isFavorite = false // Устанавливаем isFavorite в false
            viewModel.saveOrUpdateVacancy(vacancy) // Сохраняем изменения в базе данных
            updateFavorites() // Обновляем список избранных вакансий
        }

        binding.rcFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.rcFavorites.adapter = favoritesAdapter

        // Подписка на изменения в избранных вакансиях
        viewModel.favoriteVacancies.observe(viewLifecycleOwner) { favoriteVacancies ->
            favoritesAdapter.updateVacancies(favoriteVacancies) // Обновляем адаптер

            // Обновляем текст count_favorites
            val count = favoriteVacancies.size
            binding.countFavorites.text = "$count ${getVacancyDeclension(count)}"
        }

        // Вызовите метод для загрузки избранных вакансий
        viewModel.loadFavoriteVacancies()
    }

    private fun updateFavorites() {
        lifecycleScope.launch {
            val favoriteVacancies = viewModel.favoriteVacancies.value ?: emptyList() // Получаем текущий список избранных вакансий
            favoritesAdapter.updateVacancies(favoriteVacancies) // Обновляем адаптер

            // Обновляем текст count_favorites
            val count = favoriteVacancies.size
            binding.countFavorites.text = "$count ${getVacancyDeclension(count)}"
        }
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
        _binding = null
    }
}