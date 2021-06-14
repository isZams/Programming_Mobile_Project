package com.example.programming_mobile_project.login

import androidx.lifecycle.ViewModel
import com.example.programming_mobile_project.database.UtenteDB
import com.example.programming_mobile_project.models.Utente
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

/**
    AuthViewModel utilizzato per gestire la logica dell'autenticazione
    auth per instanziare FirebaseAuth
    userDB oggetto della classe UtenteDB
 */

class AuthViewModel() : ViewModel() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userDB = UtenteDB()


    /**
    * isLoggedIn funzione che ritorna se l'utente è connesso e quindi ha fatto il login
    * @return true se l'utente è connesso, false se non lo è
    */
    fun isLoggedIn(): Boolean = auth.currentUser?.uid.isNullOrBlank().not()

    /**
        @return l'id dell'utente attualmente connesso
     */
    fun uid(): String? = auth.currentUser?.uid

    /**
    * @param email stringa che viene passata dall'utente quando inserisce l'email nell'editText
    * @param password stringa che viene passata dall'utente quando inserisce la password nell'editText
    * @return l'utente registrato con email e password
     */
    suspend fun signUp(email: String, password: String): FirebaseUser? {
        return try {
            val response = auth.createUserWithEmailAndPassword(email, password).await()
            response.user
        } catch (e: Exception) {
            null
        }
    }

    /**
    * @param email stringa che viene passata dall'utente quando inserisce l'email nell'editText
    * @param password stringa che viene passata dall'utente quando inserisce la password nell'editText
    * @return l'utente loggato con email e password
    */
    suspend fun sigIn(email: String, password: String): FirebaseUser? {
        return try {
            val signin = auth.signInWithEmailAndPassword(email, password).await()
            signin.user
        } catch (e: Exception) {
            null
        }
    }

    /**
    metodo che richiama la funzione signOut() della classe FirebaseAuth per il logout dell'utente
     */
    fun logOut() {
        auth.signOut()
    }

    /**
    * @param utente oggetto della data class utente
    * funzione che prende l'utente attualmente registrato e chiama la funzione addUtente di UserDB per la registrazione nel DB
     */
    suspend fun addAuthUserOnDB(utente: Utente) {
        try {
            val user = auth.currentUser
            if (user != null) {
                userDB.addUtente(user.uid, utente)
            }
        } catch (e: Exception) {
        }
    }
}