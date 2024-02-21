package com.example.onlinestore.ui.loginScreen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.onlinestore.R
import com.example.onlinestore.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private val binding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        with(binding) {
            var validFirstName = false
            var validLastName = false
            var validPhoneNumber = false

            val firstName = etFirstName
            val clearFirstName = btnResetFirstName
            val llFirstName = llFirstName

            val lastName = etLastName
            val clearLastName = btnResetLastName
            val llLastName = llLastName

            val phoneNumber = etPhoneNumber
            val clearPhoneNumber = btnResetPhoneNumber

            val defaultHint = "Номер телефона"
            val phoneNumberHint = "+7 XXX XXX-XX-XX"

            firstName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (validateInput(firstName, clearFirstName, llFirstName)) {
                        validFirstName = true
                    }
                    btnEnter.isEnabled = validFirstName && validLastName && validPhoneNumber
                }
            })

            clearFirstName.setOnClickListener {
                firstName.text.clear()
                llFirstName.setBackgroundResource(R.drawable.shape_background)
            }

            lastName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (validateInput(lastName, clearLastName, llLastName)) {
                        validLastName = true
                    }
                    btnEnter.isEnabled = validFirstName && validLastName && validPhoneNumber
                }
            })

            clearLastName.setOnClickListener {
                lastName.text.clear()
                llLastName.setBackgroundResource(R.drawable.shape_background)
            }

            etPhoneNumber.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    etPhoneNumber.hint = phoneNumberHint
                } else {
                    etPhoneNumber.hint = defaultHint
                }
            }

            phoneNumber.addTextChangedListener(object : TextWatcher {
                private var mSelfChange = false

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s == null || mSelfChange) {
                        return
                    }

                    val preparedStr = s.replace(Regex("(\\D*)"), "")
                    var resultStr = ""
                    for (i in preparedStr.indices) {
                        resultStr = when (i) {
                            0 -> resultStr.plus("+7 ")
                            1 -> resultStr.plus(preparedStr[i])
                            2 -> resultStr.plus(preparedStr[i])
                            3 -> resultStr.plus(preparedStr[i])
                            4 -> resultStr.plus(" ".plus(preparedStr[i]))
                            5 -> resultStr.plus(preparedStr[i])
                            6 -> resultStr.plus(preparedStr[i])
                            7 -> resultStr.plus("-".plus(preparedStr[i]))
                            8 -> resultStr.plus(preparedStr[i])
                            9 -> resultStr.plus("-".plus(preparedStr[i]))
                            10 -> resultStr.plus(preparedStr[i])
                            else -> resultStr
                        }
                    }

                    mSelfChange = true
                    val oldSelectionPos = phoneNumber.selectionStart
                    val isEdit = phoneNumber.selectionStart != phoneNumber.length()
                    phoneNumber.setText(resultStr)
                    if (isEdit) {
                        phoneNumber.setSelection(
                            if (oldSelectionPos > resultStr.length) resultStr.length
                            else oldSelectionPos
                        )
                    } else {
                        phoneNumber.setSelection(resultStr.length)
                        clearPhoneNumber.visibility = View.VISIBLE
                    }

                    if (resultStr.length == 16) validPhoneNumber = true
                    mSelfChange = false
                }

                override fun afterTextChanged(s: Editable?) {
                    btnEnter.isEnabled = validFirstName && validLastName && validPhoneNumber
                }
            })

            clearPhoneNumber.setOnClickListener {
                phoneNumber.setText("")
                validPhoneNumber = false
                clearPhoneNumber.visibility = View.GONE
            }

            btnEnter.setOnClickListener {
                viewModel.addUser(
                    firstName.text.toString(),
                    lastName.text.toString(),
                    phoneNumber.text.toString()
                )
                openGeneralScreen()
            }
        }
    }

    private fun validateInput(
        usernameInput: EditText,
        clearIcon: ImageButton,
        linearLayout: LinearLayout
    ): Boolean {
        var validate = false
        val username = usernameInput.text.toString()

        if (username.matches(Regex("^[а-яА-ЯёЁ]+$"))) {
            linearLayout.setBackgroundResource(R.drawable.shape_background)
            clearIcon.visibility = View.VISIBLE
            clearIcon.setBackgroundResource(R.color.lightGrey)
            validate = true
        } else {
            linearLayout.setBackgroundResource(R.drawable.shape_red_background)
            clearIcon.visibility = View.VISIBLE
            clearIcon.setBackgroundResource(R.color.lightRed)
        }

        if (username.isEmpty()) {
            linearLayout.setBackgroundResource(R.drawable.shape_background)
            clearIcon.setBackgroundResource(R.color.lightGrey)
            clearIcon.visibility = View.GONE
        }
        return validate
    }

    private fun openGeneralScreen() {
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToCatalogFragment()
        )
    }
}