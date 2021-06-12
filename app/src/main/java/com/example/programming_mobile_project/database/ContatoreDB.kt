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
     * Aggiorna i contatori di uno chalet decrementandoli.
     * @param quantita specifica di quanto i contatori devono essere decrementati (i valori devono essere positivi)
     * @return [key_chalet] oppure null
     */
    suspend fun updateContatore(key_chalet: String, quantita: Contatore): String? {
        return try {
            contatoreRef
                .document(key_chalet)
                .update(
                    hashMapOf<String, Any>(
                        "current_lettini" to FieldValue.increment(
                            quantita.current_lettini.unaryMinus().toLong()
                        ),
                        "current_sdraie" to FieldValue.increment(
                            quantita.current_sdraie.unaryMinus().toLong()
                        ),
                        "current_sedie" to FieldValue.increment(
                            quantita.current_sedie.unaryMinus().toLong()
                        ),
                    )
                ).await()
            key_chalet
        } catch (e: Exception) {
            null
        }


    }
}