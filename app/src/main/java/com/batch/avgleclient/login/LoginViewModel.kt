package com.batch.avgleclient.login

import android.app.Activity
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.batch.avgleclient.R
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    fun signInWithEmail(activity: Activity, auth: FirebaseAuth, email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(getApplication(), user.toString(), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        getApplication(),
                        getApplication<Application>().getText(R.string.please_retry),
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}