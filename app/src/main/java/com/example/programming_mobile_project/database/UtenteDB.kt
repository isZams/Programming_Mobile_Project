package com.example.programming_mobile_project.database
import android.util.Log
import com.example.programming_mobile_project.models.Utente
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UtenteDB : FirebaseDB() {

    val utenteRef = db.collection("utenti")
    val key_user: String = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    suspend fun getUtente(): Utente? {
        return try {
            //val user
            val utente = utenteRef
                .document(key_user)
                .get()
                .await()
            utente.toObject<Utente>()
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Funzione che prende in input un id ed un Utente e lo inserisce all'interno del database
     * @param id id dell'utente attuale
     * @param utente utente da aggiungere al database
     */
    suspend fun addUtente(id: String, utente: Utente) {
        try {
            utenteRef.document(id).set(utente).await()
        } catch (e: Exception) {}
    }

    /**
     * Funzione che modifica prende l'id dell'utente attualmente loggato e modifica le informazioni
     * contenute nel database
     * @param utente informazioni utente da modificare
     */
    suspend fun modificaUtente(utente: Utente){
        try{
            utenteRef.document(key_user).set(utente).await()
        }
        catch (e: Exception){}
    }

    /**
     * Funzione che in automatico prende l'utente autenticato e invia alla propria mail la procedura
     * di ripristino della password
     */
    fun resetPassword(){
        Firebase.auth.currentUser?.email?.let { Firebase.auth.sendPasswordResetEmail(it) }
    }
}

