package com.example.jobsearchapp.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobsearchapp.MyApplication
import com.example.jobsearchapp.databinding.FragmentFavoritesBinding
import com.example.jobsearchapp.presentation.about.MyBottomSheetDialogFragment
import com.example.jobsearchapp.presentation.favorites.view_model.FavoriteViewModel
import com.example.jobsearchapp.presentation.favorites.view_model.FavoriteViewModelFactory
import com.example.jobsearchapp.presentation.search.vacancies_list.VacanciesAdapter
import com.example.jobsearchapp.presentation.search.vacancies_list.VacancyUI
import javax.inject.Inject

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: FavoriteViewModelFactory
    private val viewModel: FavoriteViewModel by viewModels { viewModelFactory }
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
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setViewModel()
    }

    private fun setAdapter() = with(binding) {
        favoritesAdapter = VacanciesAdapter(
            onClick = { vacancy ->
                vacancy.isFavorite = false
                viewModel.saveOrUpdateVacancy(vacancy)
                updateFavorites()
            },
            onRespondClick = { vacancy ->
                respondToVacancy(vacancy)
            }
        )
        rcFavorites.layoutManager = LinearLayoutManager(requireContext())
        rcFavorites.adapter = favoritesAdapter
    }

    private fun setViewModel() {
        viewModel.favoriteVacancies.observe(viewLifecycleOwner) { favoriteVacancies ->
            favoritesAdapter.submitList(favoriteVacancies)
            val count = favoriteVacancies.size
            val tempDeclension = "$count ${viewModel.getVacancyDeclension(count, resources)}"
            binding.countFavorites.text = tempDeclension
        }
        viewModel.loadFavoriteVacancies()
    }

    private fun respondToVacancy(vacancy: VacancyUI) {
        val bundle = Bundle().apply {
            putString("title", vacancy.title)
        }
        val bottomSheet = MyBottomSheetDialogFragment()
        bottomSheet.arguments = bundle
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }

    private fun updateFavorites() {
        viewModel.loadFavoriteVacancies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}