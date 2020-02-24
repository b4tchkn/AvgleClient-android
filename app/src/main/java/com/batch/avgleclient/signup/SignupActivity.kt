package com.batch.avgleclient.signup

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.TextView
import android.widget.Toast
import com.batch.avgleclient.R
import com.batch.avgleclient.login.LoginActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*
import timber.log.Timber

class SignupActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        signup_button.setOnClickListener {
            val isEmailEmpty = TextUtils.isEmpty(edit_text_input_email.text)
            val isPassEmpty = TextUtils.isEmpty(edit_text_input_password.text)
            val isConfirmPassEmpty = TextUtils.isEmpty(edit_text_input_confirm_password.text)
            displayIsEmptyError(isEmailEmpty, text_input_email)
            displayIsEmptyError(isPassEmpty, text_input_password)
            displayIsEmptyError(isConfirmPassEmpty, text_input_confirm_password)

            when (!isPassEmpty && !isConfirmPassEmpty) {
                true -> {
                    if (
                        edit_text_input_password.text.toString() ==
                        edit_text_input_confirm_password.text.toString()
                    ) {
                        if (!isEmailEmpty) {
                            createAccount(
                                edit_text_input_email.text.toString(),
                                edit_text_input_password.text.toString()
                            )
                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            getText(R.string.incorrect_password),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        edit_text_input_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length < MIN_PASS_LENGTH) {
                    text_input_password.error = getString(R.string.passoword_min_length_error)
                } else {
                    text_input_password.error = null
                }
            }
        })

        edit_text_input_confirm_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length < MIN_PASS_LENGTH) {
                    text_input_confirm_password.error = getString(R.string.passoword_min_length_error)
                } else {
                    text_input_confirm_password.error = null
                }
            }
        })
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = LoginActivity.createIntent(this)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    Toast.makeText(
                        applicationContext,
                        getText(R.string.create_account_success),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Timber.tag("SignupActivity").d(task.exception)
                    Toast.makeText(
                        applicationContext,
                        getText(R.string.create_account_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun displayIsEmptyError(isEmpty: Boolean, textView: TextInputLayout) {
        when (isEmpty) {
            true -> textView.error = getString(R.string.is_empty)
            false -> textView.error = null
        }
    }

    companion object {
        private const val MIN_PASS_LENGTH = 6
        fun createIntent(activity: Activity) = Intent(activity, SignupActivity::class.java)
    }
}
