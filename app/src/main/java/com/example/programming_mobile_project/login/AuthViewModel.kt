package com.example.programming_mobile_project.login

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.programming_mobile_project.Home_Page.HomePage
import com.example.programming_mobile_project.R
import com.example.programming_mobile_project.database.UtenteDB
import com.example.programming_mobile_project.models.Utente
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class AuthViewModel(private val context: Context): ViewModel() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userDB = UtenteDB()

    fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SING UP SUCCESS", "createUserWithEmail:success")
                    val user = auth.currentUser

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SING UP FAILED", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
    fun sigIn(email: String, password: String, view: View){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SING IN SUCCESS", "signInWithEmail:success")
                    val user = auth.currentUser
                    view.findNavController().navigate(R.id.action_loginAuthFragment_to_HomePage)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SING IN FAILED", "signInWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }


    fun logOut(){
        Firebase.auth.signOut()
    }

    fun addAuthUserOnDB(utente: Utente){
        try {
            val user = auth.currentUser
            viewModelScope.launch {
                // launch è un bridge tra il mondo delle funzioni normali e quello delle coroutine
                if (user != null) {
                    userDB.addUtente(user.uid, utente)
                }

            }
        }catch (e: Exception){}

    }


}