package com.batch.avgleclient.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.batch.avgleclient.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        signin_button.setOnClickListener {
            val isEmailEmpty = TextUtils.isEmpty(edit_text_input_email.text)
            val isPasswordEmpty = TextUtils.isEmpty(edit_text_input_password.text)

            if (!isEmailEmpty && !isPasswordEmpty) {
                viewModel.signInWithEmail(this,
                    auth,
                    edit_text_input_email.text.toString(),
                    edit_text_input_password.text.toString())
                text_input_email.error = null
                text_input_password.error = null
            } else {
                when (isEmailEmpty) {
                    true -> text_input_email.error = getString(R.string.is_empty)
                    false -> text_input_email.error = null
                }
                when (isPasswordEmpty) {
                    true -> text_input_password.error = getString(R.string.is_empty)
                    false -> text_input_password.error = null
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
    }
}
