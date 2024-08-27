package com.example.jobsearchapp.feature.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.jobsearchapp.R
import com.example.jobsearchapp.databinding.FragmentLoginScreenBinding
import com.example.jobsearchapp.feature.auth.view_model.AuthViewModel

class LoginScreen : Fragment() {

    private var _binding: FragmentLoginScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEditText()
        setupContinueButton()
        updateContinueButtonState("")
    }

    private fun setupEditText() = with(binding) {
        edEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    edEmail.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.email,
                        0,
                        0,
                        0
                    )
                    imEmailClean.visibility = View.GONE
                    tvErrorEmail.visibility = View.GONE
                    edEmail.backgroundTintList =
                        resources.getColorStateList(R.color.grey2, null)
                } else {
                    edEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    imEmailClean.visibility = View.VISIBLE
                    tvErrorEmail.visibility = View.GONE
                    edEmail.backgroundTintList =
                        resources.getColorStateList(R.color.grey2, null)
                }
                edEmail.setTextColor(resources.getColor(R.color.grey5))
                updateContinueButtonState(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        imEmailClean.setOnClickListener {
            edEmail.text.clear()
            imEmailClean.visibility = View.GONE
            edEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.email, 0, 0, 0)
            tvErrorEmail.visibility = View.GONE
        }
    }

    private fun setupContinueButton() = with(binding) {
        bContinue.setOnClickListener {
            val input = edEmail.text.toString()
            if (viewModel.isEmailValid(input)) {
                val bundle = Bundle().apply {
                    putString("email", input)
                }
                findNavController().navigate(R.id.action_loginScreen_to_loginConfirmScreen, bundle)
            } else {
                edEmail.setTextColor(resources.getColor(R.color.red))
                tvErrorEmail.visibility = View.VISIBLE
            }
        }
    }

    private fun updateContinueButtonState(input: String) {
        if (input.isNotEmpty()) {
            binding.bContinue.isEnabled = true
            binding.bContinue.backgroundTintList = resources.getColorStateList(R.color.blue, null)
        } else {
            binding.bContinue.isEnabled = false
            binding.bContinue.backgroundTintList =
                resources.getColorStateList(R.color.dark_blue, null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}