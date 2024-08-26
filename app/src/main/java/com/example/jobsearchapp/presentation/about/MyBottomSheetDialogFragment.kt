package com.example.jobsearchapp.presentation.about

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.jobsearchapp.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MyBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dimBackground.visibility = View.VISIBLE
        handleArguments()
        addAccompListener()
        bRespondListener()
    }

    private fun handleArguments() {
        val title = arguments?.getString("title")
        val question = arguments?.getString("question")

        when {
            question != null && title != null -> {
                binding.tvVacancyName.text = title
                binding.tvAddAccomp.visibility = View.GONE
                binding.edAccomp.visibility = View.VISIBLE
                binding.edAccomp.setText(question) // Устанавливаем текст вопроса в EditText
            }
            title != null -> {
                binding.tvVacancyName.text = title
                binding.tvAddAccomp.visibility = View.VISIBLE
                binding.edAccomp.visibility = View.GONE
            }
            else -> {
                binding.tvAddAccomp.visibility = View.GONE
                binding.edAccomp.visibility = View.GONE
            }
        }
    }

    private fun addAccompListener() = with(binding) {
        tvAddAccomp.setOnClickListener {
            tvAddAccomp.visibility = View.GONE
            edAccomp.visibility = View.VISIBLE
        }
    }

    private fun bRespondListener() {
        binding.bRespondBSheet.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Вы откликнулись!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}