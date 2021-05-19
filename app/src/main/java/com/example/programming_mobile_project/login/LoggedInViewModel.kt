package com.example.programming_mobile_project.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

class LoggedInViewModel(application: Application) : AndroidViewModel(application){
    private var authRepository = AuthRepo()
    private var _authenticatedUser = MutableLiveData<FirebaseUser>()
    private var _loggedOutLiveData = MutableLiveData<Boolean>()

    init {
        _authenticatedUser = authRepository.getUserLiveData()
        _loggedOutLiveData = authRepository.getLoggedOutLiveData()
    }

    fun logOut(){
        authRepository.logOut()
    }

    fun getauthenticatedUser(): MutableLiveData<FirebaseUser> {
        return _authenticatedUser
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean> {
        return _loggedOutLiveData
    }
}