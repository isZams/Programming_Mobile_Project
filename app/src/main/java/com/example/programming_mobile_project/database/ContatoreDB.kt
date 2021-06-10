package com.example.programming_mobile_project.database

import com.example.programming_mobile_project.models.Contatore
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class ContatoreDB : FirebaseDB() {
    val contatoreRef = db.collection("contatori")

    /**
     * Imposta il contatore di uno chalet
     * @return Ritorna success se operazione conclusa con successo altrimenti null
     */
    suspend fun setContatore(chaletKey: String, contatore: Contatore): String? {
        try {
            contatoreRef
                .document(chaletKey)
                .set(contatore)
                .await()
            return "success"
        } catch (e: Exception) {
            return null
        }

    }

    /**
     * Restituisce il contatore di uno chalet
     * @return se operazione conclusa con successo ritorna il contatore altrimenti null
     */
    suspend fun getContatore(chaletKey: String): Contatore? {
        return try {
            contatoreRef
                .document(chaletKey)
                .get()
                .await().toObject<Contatore>()
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Applica un delta al contatore dei lettini
     * @param value delta (positivo o negativo) applicato al contatore. Di default vale 1
     * @return "success" altrimenti null
     */
    suspend fun incrementLettini(chaletKey: String, value: Long = 1): String? {
        return try {
            contatoreRef
                .document(chaletKey)
                .update("current_lettini", FieldValue.increment(value))
                .await()
            "success"
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Applica un delta al contatore delle sedie
     * @param value delta (positivo o negativo) applicato al contatore. Di default vale 1
     * @return "success" altrimenti null
     */
    suspend fun incrementSedie(chaletKey: String, value: Long = 1): String? {
        return try {
            contatoreRef
                .document(chaletKey)
                .update("current_sedie", FieldValue.increment(value))
                .await()
            "success"
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Applica un delta al contatore delle sdraie
     * @param value delta (positivo o negativo) applicato al contatore. Di default vale 1
     * @return "success" altrimenti null
     */
    suspend fun incrementSdraie(chaletKey: String, value: Long = 1): String? {
        return try {
            contatoreRef
                .document(chaletKey)
                .update("current_sdraie", FieldValue.increment(value))
                .await()
            "success"
        } catch (e: Exception) {
            null
        }
    }
}