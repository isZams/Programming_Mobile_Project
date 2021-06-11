package com.example.programming_mobile_project.database
import com.example.programming_mobile_project.models.Utente
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.toObject
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
            utente.toObject()
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Funzione che prende in input un id ed un Utente e lo inserisce all'interno del database
     * @param id id dell'utente attuale
     * @param utente utente da aggiungere al database
     */
    suspend fun addUtente(id: String, utente: Utente){
        try {
            val response = utenteRef.document(id).set(utente).await()
        } catch (e: Exception) {
        }
    }
}

