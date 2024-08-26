package com.example.jobsearchapp.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobsearchapp.MyApplication
import com.example.jobsearchapp.data.model.Vacancy
import com.example.jobsearchapp.databinding.FragmentFavoritesBinding
import com.example.jobsearchapp.presentation.about.MyBottomSheetDialogFragment
import com.example.jobsearchapp.presentation.search.MainViewModel
import com.example.jobsearchapp.presentation.search.VacanciesAdapter
import com.example.jobsearchapp.viewmodel.ViewModelFactory
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
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setViewModel()
    }

    private fun setAdapter()=with(binding){

        favoritesAdapter = VacanciesAdapter(emptyList(), { vacancy ->
            vacancy.isFavorite = false
            viewModel.saveOrUpdateVacancy(vacancy)
            updateFavorites()
        }, { vacancy ->
            respondToVacancy(vacancy)
        })

        rcFavorites.layoutManager = LinearLayoutManager(requireContext())
        rcFavorites.adapter = favoritesAdapter
    }

    private fun setViewModel(){

        viewModel.favoriteVacancies.observe(viewLifecycleOwner) { favoriteVacancies ->
            favoritesAdapter.updateVacancies(favoriteVacancies)
            val count = favoriteVacancies.size
            val temoDeclension="$count ${viewModel.getVacancyDeclension(count, resources)}"
            binding.countFavorites.text = temoDeclension
        }

        viewModel.loadFavoriteVacancies()
    }

    private fun respondToVacancy(vacancy: Vacancy) {
        val bundle = Bundle().apply {
            putString("title", vacancy.title)
        }

        val bottomSheet = MyBottomSheetDialogFragment()
        bottomSheet.arguments = bundle
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }

    private fun updateFavorites() {
        lifecycleScope.launch {
            val favoriteVacancies = viewModel.favoriteVacancies.value
                ?: emptyList()
            favoritesAdapter.updateVacancies(favoriteVacancies)

            val count = favoriteVacancies.size
            val tempVacancyDec="$count ${viewModel.getVacancyDeclension(count, resources)}"
            binding.countFavorites.text = tempVacancyDec
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}