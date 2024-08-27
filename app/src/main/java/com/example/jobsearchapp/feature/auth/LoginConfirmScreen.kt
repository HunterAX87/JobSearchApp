package com.example.jobsearchapp.feature.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.jobsearchapp.R
import com.example.jobsearchapp.databinding.FragmentLoginConfirmScreenBinding
import com.example.jobsearchapp.feature.auth.view_model.AuthViewModel

class LoginConfirmScreen : Fragment() {
    private val viewModel: AuthViewModel by viewModels()

    private var _binding: FragmentLoginConfirmScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginConfirmScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupConfirmButton()

        val email = arguments?.getString("email")
        binding.tvCodSended.append(" $email")

        viewModel.isConfirmButtonEnabled.observe(viewLifecycleOwner) { isEnabled ->
            binding.bConfirm.isEnabled = isEnabled
            binding.bConfirm.backgroundTintList = if (isEnabled) {
                resources.getColorStateList(R.color.blue, null)
            } else {
                resources.getColorStateList(R.color.dark_blue, null)
            }
        }
        setupPinCodeInput()
    }

    private fun setupPinCodeInput() {
        val editTexts = arrayOf(
            binding.editText1,
            binding.editText2,
            binding.editText3,
            binding.editText4
        )
        viewModel.setupPinCodeInput(editTexts)
    }

    private fun setupConfirmButton() {
        binding.bConfirm.setOnClickListener {
            val code = binding.editText1.text.toString() +
                    binding.editText2.text.toString() +
                    binding.editText3.text.toString() +
                    binding.editText4.text.toString()

            if (viewModel.isConfirmCodeValid(code)) {
                findNavController().navigate(
                    R.id.action_loginConfirmScreen_to_mainScreen
                )
            } else {
                binding.bConfirm.backgroundTintList =
                    resources.getColorStateList(R.color.dark_blue, null)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}