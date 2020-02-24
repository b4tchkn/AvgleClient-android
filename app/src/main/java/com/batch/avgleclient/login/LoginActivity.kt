package com.batch.avgleclient.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.batch.avgleclient.MainActivity
import com.batch.avgleclient.R
import com.batch.avgleclient.signup.SignupActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progress_bar.isVisible = false
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        signin_button.setOnClickListener {
            updateUI(true)
            val isEmailEmpty = TextUtils.isEmpty(edit_text_input_email.text)
            val isPasswordEmpty = TextUtils.isEmpty(edit_text_input_password.text)
            if (!isEmailEmpty && !isPasswordEmpty) {
                signInWithEmail()
                text_input_email.error = null
                text_input_password.error = null
            } else {
                updateUI(false)
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
        google_button.setOnClickListener {
            updateUI(true)
            signInWithGoogle()
        }
        signup_button.setOnClickListener {
            val intent = SignupActivity.createIntent(this)
            startActivity(intent)
        }
    }

    private fun signInWithEmail() {
        val email = edit_text_input_email.text.toString()
        val password = edit_text_input_password.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    updateUI(false)
                } else {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_retry),
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(false)
                }
            }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    updateUI(false)
                } else {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_retry),
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(false)
                }
            }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Timber.tag(TAG + "error").d(e.toString())
                updateUI(false)
            }
        }
    }

    private fun updateUI(isProcessing: Boolean) {
        progress_bar.isVisible = isProcessing
        signin_button.isVisible = !isProcessing
        signup_button.isVisible = !isProcessing
        text_orsignup.isVisible = !isProcessing
        google_button.isVisible = !isProcessing
        val user = auth.currentUser
        if (!isProcessing && user != null) {
            val intent = MainActivity.createIntent(this)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

    companion object {
        private const val TAG = "AvLogin"
        private const val RC_SIGN_IN = 123
        fun createIntent(activity: Activity) = Intent(activity, LoginActivity::class.java)
    }
}
