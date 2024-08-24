package com.example.jobsearchapp.presentation.authorization

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.jobsearchapp.R
import com.example.jobsearchapp.databinding.FragmentLoginScreenBinding

class LoginScreen : Fragment() {

    private var _binding: FragmentLoginScreenBinding? = null
    private val binding get() = _binding!!

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

    private fun setupEditText() {
        binding.edEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.edEmail.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.email,
                        0,
                        0,
                        0
                    )
                    binding.imEmailClean.visibility = View.GONE
                    binding.tvErrorEmail.visibility = View.GONE
                    binding.edEmail.backgroundTintList =
                        resources.getColorStateList(R.color.grey2, null)
                } else {
                    binding.edEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    binding.imEmailClean.visibility = View.VISIBLE
                    binding.tvErrorEmail.visibility = View.GONE
                    binding.edEmail.backgroundTintList =
                        resources.getColorStateList(R.color.grey2, null)
                }
                binding.edEmail.setTextColor(resources.getColor(R.color.grey5))
                updateContinueButtonState(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.imEmailClean.setOnClickListener {
            binding.edEmail.text.clear()
            binding.imEmailClean.visibility = View.GONE
            binding.edEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.email, 0, 0, 0)
            binding.tvErrorEmail.visibility = View.GONE
        }
    }

    private fun setupContinueButton() {
        binding.bContinue.setOnClickListener {
            val input = binding.edEmail.text.toString()
            if (isValidEmailOrPhone(input)) {
                val bundle = Bundle().apply {
                    putString("email", input)
                }
                findNavController().navigate(
                    R.id.action_loginScreen_to_loginConfirmScreen,
                    bundle
                )
            } else {
                binding.edEmail.setTextColor(resources.getColor(R.color.red))
                binding.tvErrorEmail.visibility = View.VISIBLE
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

    private fun isValidEmailOrPhone(input: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        val phoneRegex = "^\\+?[0-9]{10,15}$".toRegex()
        return emailRegex.matches(input) || phoneRegex.matches(input)
    }

//    private fun setRedBorder() {
//        val redBorder = GradientDrawable().apply {
//            shape = GradientDrawable.RECTANGLE
//            setColor(resources.getColor(R.color.grey2))
//            setStroke(2, resources.getColor(R.color.red))
//            cornerRadius = 8f
//        }
//        binding.edEmail.background = redBorder
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}