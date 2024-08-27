package com.example.jobsearchapp.presentation.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.jobsearchapp.MyApplication
import com.example.jobsearchapp.R
import com.example.jobsearchapp.databinding.FragmentAboutBinding
import com.example.jobsearchapp.presentation.search.view_model.SearchMainViewModel
import com.example.jobsearchapp.presentation.search.vacancies_list.VacancyUI
import com.example.jobsearchapp.presentation.search.view_model.SearchViewModelFactory
import javax.inject.Inject

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var searchViewModelFactory: SearchViewModelFactory
    private val viewModel: SearchMainViewModel by viewModels { searchViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vacancy: VacancyUI = arguments?.getSerializable("vacancy") as VacancyUI
        vacancy.let { vac ->
            setInformation(vac)
            setQuestionsContainer(vac)
        }
        respondToVacancy(vacancy)
    }

    private fun setInformation(vac: VacancyUI) = with(binding) {
        tvTitle.text = vac.title
        tvSalary.text = vac.salary.full ?: getString(R.string.salary_not_exist)
        tvExperience.text = vac.experience.previewText
        tvSchedules.text =
            vac.schedules.joinToString(", ") { schedule -> schedule.capitalize() }

        vac.appliedNumber.takeIf { it > 0 }?.let { appliedNumber ->
            val tempApplied = "$appliedNumber ${getString(R.string.applied_number)}"
            tvAppliedNumber.text = tempApplied
        }

        imBackk.setOnClickListener {
            findNavController().navigateUp()
        }

        vac.lookingNumber.takeIf { it > 0 }?.let { lookingNumber ->
            val tempLooking = "$lookingNumber ${getString(R.string.looking_number)}"
            tvLookingNumber.text = tempLooking
        }

        tvComp.text = vac.company
        val tempAdress = "${vac.address.town}, ${vac.address.street}, ${vac.address.house}"
        tvAdress.text = tempAdress

        tvDescription.text = vac.description ?: ""
        tvResponsibilities.text = vac.responsibilities?.replace("\n", "\n") ?: ""
    }


    private fun setQuestionsContainer(vac: VacancyUI) = with(binding) {
        questionsContainer.removeAllViews()

        vac.questions.forEach { question ->
            val questionView = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_question, questionsContainer, false) as CardView
            val questionTextView: TextView = questionView.findViewById(R.id.question_text)
            questionTextView.text = question
            questionView.setOnClickListener {
                respondToQuestion(question, vac.title)
            }
            questionsContainer.addView(questionView)
        }
        updateFavoriteIcon(vac.isFavorite)
        imFavorite.setOnClickListener {
            vac.isFavorite = !vac.isFavorite
            updateFavoriteIcon(vac.isFavorite)
            viewModel.saveOrUpdateVacancy(vac)
        }
    }

    private fun respondToQuestion(question: String, vacancy: String) {
        val bundle = Bundle().apply {
            putString("question", question)
            putString("title", vacancy)
        }
        val bottomSheet = MyBottomSheetDialogFragment()
        bottomSheet.arguments = bundle
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }

    private fun respondToVacancy(vacancy: VacancyUI?) {
        binding.bRespondAbout.setOnClickListener {
            val bundle = Bundle().apply {
                putString("title", vacancy?.title)
            }
            val bottomSheet = MyBottomSheetDialogFragment()
            bottomSheet.arguments = bundle
            bottomSheet.show(
                parentFragmentManager,
                bottomSheet.tag
            )
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