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
}

