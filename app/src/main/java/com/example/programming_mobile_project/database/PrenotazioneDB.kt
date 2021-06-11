package com.example.programming_mobile_project.database

import com.example.programming_mobile_project.models.Prenotazione
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class PrenotazioneDB : FirebaseDB() {
    val prenotazioniRef = db.collection("prenotazioni")

    /**
     *  Controlla se l'ombrellone è gia occupato e successivamente effettua la prenotazione
     *  @return L'id della prenotazione altrimenti un oggetto null
     */
    suspend fun setPrenotazione(prenotazione: Prenotazione): String? {
        try {
            val transaction = db.runTransaction {
                prenotazioniRef
                    .whereEqualTo("key_chalet", prenotazione.key_chalet)
                    .whereEqualTo("n_ombrellone", prenotazione.n_ombrellone)
                    .get()

            }.await()
            if (transaction.result.isEmpty) {
                //ombrellone libero
                val p = prenotazioniRef
                    .add(prenotazione).await()
                return p.id
            }
            // ombrellone occupato
            return null
        } catch (e: Exception) {
            return null
        }
    }

    /**
     * @param key_utente la chiave dell'utente di cui si vuole ottenere le prenotazioni
     * @return il relativo oggetto [Query]
     */
    fun queryPrenotazioniByUtente(key_utente: String): Query {
        return prenotazioniRef.whereEqualTo("key_utente", key_utente)
            .orderBy("timestamp_prenotazione")
    }


    /**
     * Fa uso di [queryPrenotazioniByUtente]
     * @param key_utente chiave dell'utente
     * @return Ritorna la lista delle prenotazioni, altrimenti null in caso di errore
     */
    suspend fun getPrenotazioniByUtente(key_utente: String): List<Prenotazione>? {
        return try {
            queryPrenotazioniByUtente(key_utente)
                .get().await().toObjects()
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Dato l'id della prenotazione ritorna le info della prenotazione corrispondente
     * @param key_prenotazione key della prenotazione
     * @return DocumentSnapshot?, se la prenotazione è stata trovata è popolato, altrimenti è null
     */
    suspend fun getPrenotazione(key_prenotazione: String): Prenotazione? {
        return try {
            prenotazioniRef.document(key_prenotazione)
                .get().await().toObject()
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Restituisce, di un determinato chalet, una lista contenente i numeri di ombrellone occupati.
     * In caso di errore restituisce null
     */
    suspend fun getOmbrelloniOccupati(chaletKey: String): List<Int>? {
        return try {
            val prenotazioni = prenotazioniRef
                .whereEqualTo("key_chalet", chaletKey)
                .whereGreaterThan("data_termine_prenotazione", System.currentTimeMillis())
                .get()
                .await()
            val n_ombrelloni =
                prenotazioni.map { it.toObject<Prenotazione>().n_ombrellone }
            n_ombrelloni
        } catch (e: Exception) {
            null
        }
    }
}