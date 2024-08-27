package com.example.jobsearchapp.feature.auth.view_model

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class AuthViewModel : ViewModel() {

    private val _isConfirmButtonEnabled = MutableLiveData<Boolean>()
    val isConfirmButtonEnabled: LiveData<Boolean> = _isConfirmButtonEnabled

    fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        val phoneRegex = "^\\+?[0-9]{10,15}$".toRegex()
        return emailRegex.matches(email) || phoneRegex.matches(email)
    }

    fun isConfirmCodeValid(code: String): Boolean {
        return code.length == 4
    }

    fun setupPinCodeInput(editTexts: Array<EditText>) {
        for (i in editTexts.indices) {
            editTexts[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
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
        _isConfirmButtonEnabled.value = allFilled
    }
}