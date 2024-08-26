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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobsearchapp.MyApplication
import com.example.jobsearchapp.R
import com.example.jobsearchapp.data.model.Vacancy
import com.example.jobsearchapp.databinding.FragmentMainScreenBinding
import com.example.jobsearchapp.presentation.about.MyBottomSheetDialogFragment
import com.example.jobsearchapp.presentation.search.ofers_list.OffersAdapter
import com.example.jobsearchapp.viewmodel.ViewModelFactory
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

        initRcViews()
        offerListener()

        initFirst3Vacancies()
        buttonMoreListener()
        imageBackListener()

        viewModel.loadOffers()
        viewModel.loadVacancies()
    }


    private fun initRcViews() = with(binding) {
        offersAdapter = OffersAdapter()
        vacanciesAdapter = VacanciesAdapter(emptyList(), { vacancy ->
            viewModel.saveOrUpdateVacancy(vacancy)
        }, { vacancy ->
            respondToVacancy(vacancy)
        })

        rcViewRec.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rcViewVacancies.layoutManager = LinearLayoutManager(requireContext())

        rcViewRec.adapter = offersAdapter
        rcViewVacancies.adapter = vacanciesAdapter
    }

    private fun respondToVacancy(vacancy: Vacancy) {
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
            vacanciesAdapter.updateVacancies(initialVacancies)
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

    @SuppressLint("SetTextI18n")
    private fun buttonMoreListener() = with(binding) {
        bMore.setOnClickListener {
            vacanciesAdapter.updateVacancies(allVacancies)
            bMore.visibility = View.GONE
            rcViewRec.visibility = View.GONE
            linearMore.visibility = View.VISIBLE
            tvVacanciesFY.visibility = View.GONE
            imBack.visibility = View.VISIBLE
            vacanciesNum.text =
                "${allVacancies.size} ${
                    viewModel.getVacancyDeclension(
                        allVacancies.size,
                        resources
                    )
                }"
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