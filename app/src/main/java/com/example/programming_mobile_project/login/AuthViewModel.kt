package com.example.programming_mobile_project.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser


class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private var authRepository = AuthRepo()
    private var _authenticatedUser = MutableLiveData<FirebaseUser>()

    init {
        _authenticatedUser = authRepository.getUserLiveData()
    }

    fun signUpWithEmail(email: String, password: String) {
        authRepository.firebaseEmailSignup(email, password, getApplication())
    }

    fun signInWithEmail(email: String, password: String) {
        authRepository.firebaseEmailSigin(email, password, getApplication())
    }

    fun getAuthenticatedUser(): MutableLiveData<FirebaseUser> {
        return _authenticatedUser
    }
}