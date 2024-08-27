package com.example.jobsearchapp.presentation.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobsearchapp.MyApplication
import com.example.jobsearchapp.R
import com.example.jobsearchapp.databinding.FragmentMainScreenBinding
import com.example.jobsearchapp.presentation.about.MyBottomSheetDialogFragment
import com.example.jobsearchapp.presentation.search.ofers_list.OffersAdapter
import com.example.jobsearchapp.presentation.search.vacancies_list.VacanciesAdapter
import com.example.jobsearchapp.presentation.search.vacancies_list.VacancyUI
import com.example.jobsearchapp.presentation.search.view_model.SearchMainViewModel
import com.example.jobsearchapp.presentation.search.view_model.SearchViewModelFactory
import javax.inject.Inject

class MainScreen : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var searchViewModelFactory: SearchViewModelFactory
    private val viewModel: SearchMainViewModel by viewModels { searchViewModelFactory }
    private lateinit var offersAdapter: OffersAdapter
    private lateinit var vacanciesAdapter: VacanciesAdapter
    private var allVacancies: List<VacancyUI> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRcViews()
        offerListener()

        initFirst3Vacancies()
        buttonMoreListener()
        imageBackListener()

        viewModel.loadOffers()
        viewModel.loadVacancies(resources)
        observeOffers()
        observeVacanciesCount()
    }

    private fun initRcViews() = with(binding) {
        offersAdapter = OffersAdapter()
        vacanciesAdapter = VacanciesAdapter(
            onClick = {
                viewModel.saveOrUpdateVacancy(it)
            },
            onRespondClick = {
                respondToVacancy(it)
            }
        )

        rcViewRec.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rcViewVacancies.layoutManager = LinearLayoutManager(requireContext())
        rcViewRec.adapter = offersAdapter
        rcViewVacancies.adapter = vacanciesAdapter
    }

    private fun observeOffers() {
        viewModel.offers.observe(viewLifecycleOwner) {
            offersAdapter.submitList(it)
        }
    }

    private fun observeVacanciesCount() {
        viewModel.vacanciesCount.observe(viewLifecycleOwner) { count ->
            val tempVacanciesNum = "$count ${viewModel.getVacancyDeclension(count, resources)}"
            binding.vacanciesNum.text = tempVacanciesNum
        }
    }

    private fun respondToVacancy(vacancy: VacancyUI) {
        val bundle = Bundle().apply {
            putString("title", vacancy.title)
        }
        val bottomSheet = MyBottomSheetDialogFragment()
        bottomSheet.arguments = bundle
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }

    private fun offerListener() {
        offersAdapter.setOnOfferClickListener { link ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }
    }

    private fun initFirst3Vacancies() = with(binding) {
        viewModel.vacancies.observe(viewLifecycleOwner) { vacancies ->
            allVacancies = vacancies
            val initialVacancies = vacancies.take(3)
            vacanciesAdapter.submitList(initialVacancies)

            val bMoreText = "${getString(R.string.more)} ${vacancies.size} ${
                viewModel.getVacancyDeclension(
                    vacancies.size,
                    resources
                )
            }"
            bMore.text = bMoreText
            bMore.visibility = if (vacancies.size > 3) View.VISIBLE else View.GONE
        }
    }

    private fun buttonMoreListener() = with(binding) {
        bMore.setOnClickListener {
            vacanciesAdapter.submitList(allVacancies)
            bMore.visibility = View.GONE
            rcViewRec.visibility = View.GONE
            linearMore.visibility = View.VISIBLE
            tvVacanciesFY.visibility = View.GONE
            imBack.visibility = View.VISIBLE
        }
    }

    private fun imageBackListener() = with(binding) {
        imBack.setOnClickListener {
            rcViewRec.visibility = View.VISIBLE
            linearMore.visibility = View.GONE
            tvVacanciesFY.visibility = View.VISIBLE
            imBack.visibility = View.GONE
            initFirst3Vacancies()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}