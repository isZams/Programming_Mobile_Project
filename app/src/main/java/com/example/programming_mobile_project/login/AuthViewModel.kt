package com.example.programming_mobile_project.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private var authRepository = AuthRepo()
    private var _authenticatedUser = MutableLiveData<FirebaseUser>()
    val authenticatedUser: LiveData<FirebaseUser>
        get() = _authenticatedUser


    fun signUpWithEmail(email: String, password: String) {
        authRepository.firebaseEmailSignup(email, password, _authenticatedUser, getApplication())
    }

    fun signInWithEmail(email: String, password: String) {
        authRepository.firebaseEmailSigin(email, password, _authenticatedUser, getApplication())
    }
}