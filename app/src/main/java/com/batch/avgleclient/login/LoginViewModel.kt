package com.batch.avgleclient.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    fun signInWithEmail(auth: FirebaseAuth, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
            }
    }
}