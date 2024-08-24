package com.example.jobsearchapp.presentation.authorization

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.jobsearchapp.R
import com.example.jobsearchapp.databinding.FragmentLoginConfirmScreenBinding

class LoginConfirmScreen : Fragment() {

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
        setupPinCodeInput()
        setupConfirmButton()


        val email = arguments?.getString("email")
        binding.tvCodSended.append(" $email")


        binding.bConfirm.isEnabled = false
        binding.bConfirm.backgroundTintList = resources.getColorStateList(R.color.dark_blue, null)
    }

    private fun setupPinCodeInput() {
        val editTexts = arrayOf(
            binding.editText1,
            binding.editText2,
            binding.editText3,
            binding.editText4
        )

        for (i in editTexts.indices) {
            editTexts[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    if (s?.length == 1) {
                        if (i < editTexts.size - 1) {
                            editTexts[i + 1].requestFocus()
                        }
                    }
                    checkConfirmButtonState(editTexts)
                }

                override fun afterTextChanged(s: Editable?) {}
            })


            editTexts[i].setOnKeyListener { _, keyCode, event ->
                if (event.action == android.view.KeyEvent.ACTION_DOWN &&
                    keyCode == android.view.KeyEvent.KEYCODE_DEL
                ) {
                    if (i > 0 && editTexts[i].text.isEmpty()) {
                        editTexts[i - 1].requestFocus()
                    }
                }
                false
            }
        }
    }

    private fun checkConfirmButtonState(editTexts: Array<EditText>) {
        val allFilled = editTexts.all { it.text.length == 1 }
        binding.bConfirm.isEnabled = allFilled

        if (allFilled) {
            binding.bConfirm.backgroundTintList = resources.getColorStateList(R.color.blue, null)
        } else {
            binding.bConfirm.backgroundTintList =
                resources.getColorStateList(R.color.dark_blue, null)
        }
    }

    private fun setupConfirmButton() {
        binding.bConfirm.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginConfirmScreen_to_mainScreen
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}