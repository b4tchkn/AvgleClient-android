package com.batch.avgleclient.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.batch.avgleclient.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        signin_button.setOnClickListener {
            val isEmailEmpty = TextUtils.isEmpty(edit_text_input_email.text)
            val isPasswordEmpty = TextUtils.isEmpty(edit_text_input_password.text)

            if (!isEmailEmpty && !isPasswordEmpty) {
                signInWithEmail()
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

    private fun signInWithEmail() {
        val email = edit_text_input_email.text.toString()
        val password = edit_text_input_password.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Success!: ${user}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
