package com.batch.avgleclient.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
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
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

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
        google_button.setOnClickListener {
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
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_retry),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_retry),
                        Toast.LENGTH_SHORT
                    ).show()
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
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Timber.tag(TAG + "name").d(user.displayName)
            Timber.tag(TAG + "mail").d(user.email)
            Timber.tag(TAG + "photo").d(user.photoUrl.toString())
        }
    }

    companion object {
        private const val TAG = "AvLogin"
        private const val RC_SIGN_IN = 123
    }
}
