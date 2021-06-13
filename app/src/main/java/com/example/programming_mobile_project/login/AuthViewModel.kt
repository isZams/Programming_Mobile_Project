package com.example.programming_mobile_project.login

import androidx.lifecycle.ViewModel
import com.example.programming_mobile_project.database.UtenteDB
import com.example.programming_mobile_project.models.Utente
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthViewModel() : ViewModel() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userDB = UtenteDB()

    fun isLoggedIn(): Boolean = auth.currentUser?.uid.isNullOrBlank().not()

    fun uid(): String? = auth.currentUser?.uid

    suspend fun signUp(email: String, password: String): FirebaseUser? {
        return try {
            val response = auth.createUserWithEmailAndPassword(email, password).await()
            response.user
        } catch (e: Exception) {
            null
        }
    }

    suspend fun sigIn(email: String, password: String): FirebaseUser? {
        return try {
            val signin = auth.signInWithEmailAndPassword(email, password).await()
            signin.user
        } catch (e: Exception) {
            null
        }
    }

    fun logOut() {
        Firebase.auth.signOut()
    }

    suspend fun addAuthUserOnDB(utente: Utente) {
        try {
            val user = auth.currentUser
            if (user != null) {
                userDB.addUtente(user.uid, utente)
            }
        }
        catch (e: Exception) {
        }
    }
}