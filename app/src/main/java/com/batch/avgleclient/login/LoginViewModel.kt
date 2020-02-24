package com.batch.avgleclient.login

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.AndroidViewModel
import com.batch.avgleclient.R
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import timber.log.Timber
import kotlin.math.sign

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    fun signInWithEmail(
        activity: Activity,
        auth: FirebaseAuth,
        email: String,
        password: String): Pair<FirebaseUser?, Boolean> {
        var user: FirebaseUser? = null
        var authResult = false
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    user = auth.currentUser
                    Timber.tag(TAG+"emailSuccess").d(user.toString())
                } else {
                    user = null
                    Toast.makeText(
                        getApplication(),
                        getApplication<Application>().getText(R.string.please_retry),
                        Toast.LENGTH_SHORT).show()
                }
                authResult = task.isSuccessful
            }
        Timber.tag(TAG+"email").d(user.toString())
        return Pair(user, authResult)
    }

    fun signInWithGoogle(activity: Activity, googleSignInClient: GoogleSignInClient) {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(activity, signInIntent, RC_SIGN_IN, null)
    }

    fun firebaseAuthWithGoogle(
        activity: Activity,
        acct: GoogleSignInAccount?,
        auth: FirebaseAuth): Pair<FirebaseUser?, Boolean> {
        var user: FirebaseUser? = null
        var authResult = false
        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    user = auth.currentUser
                    Timber.tag(TAG+"googleSuccess").d(user.toString())
                } else {
                    user = null
                    Timber.tag(TAG+"error").d("失敗")
                }
                authResult = task.isSuccessful
            }
        Timber.tag(TAG+"google").d(user.toString())
        return Pair(user, authResult)
    }
    companion object {
        private const val TAG = "AvLogin"
        private const val RC_SIGN_IN = 123
    }
}