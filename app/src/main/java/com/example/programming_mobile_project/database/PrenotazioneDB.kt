package com.example.programming_mobile_project.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.programming_mobile_project.models.Prenotazione
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects

class PrenotazioneDB : FirebaseDB() {
    val prenotazioniRef = db.collection("prenotazioni")

    val _selectedPrenotazione = MutableLiveData<Prenotazione>()
    val selectedPrenotazione: LiveData<Prenotazione>
        get() = _selectedPrenotazione

    val _selectedPrenotazioni = MutableLiveData<MutableList<Prenotazione>>()
    val selectedPrenotazioni: LiveData<MutableList<Prenotazione>>
        get() = _selectedPrenotazioni

    /**
     *  Controlla se l'ombrellone è gia occupato e successivamente effettua la prenotazione
     *  @return se l'ombrellone viene correttamente prenotato viene scritto l'oggetto prenotazione in selectedPrenotazione, altrimenti viene scritto un oggetto vuoto
     */
    fun setPrenotazione(prenotazione: Prenotazione) {
        db.runTransaction {
            prenotazioniRef
                .whereEqualTo("key_chalet", prenotazione.key_chalet)
                .whereEqualTo("n_ombrellone", prenotazione.n_ombrellone)
                .get().addOnSuccessListener {
                    if (it.isEmpty) {
                        //ombrellone è libero
                        prenotazioniRef
                            .add(prenotazione)
                            .addOnSuccessListener {
                                // prenotazione effettuata con successo
                                _selectedPrenotazione.value = prenotazione
                            }
                            .addOnFailureListener {
                                _selectedPrenotazione.value = Prenotazione()
                            }
                    } else {
                        //ombrellone occupato
                        _selectedPrenotazione.value = Prenotazione()
                    }
                }
        }
    }


    /**
     *
     * @param key_utente la chiave dell'utente di cui si vuole ottenere le prenotazioni
     * @return il relativo oggetto [Query]
     */
    fun queryPrenotazioniByUtente(key_utente: String): Query {
        return prenotazioniRef.whereEqualTo("key_utente", key_utente)
            .orderBy("timestamp_prenotazione")
    }


    /**
     * Facendo uso di [queryPrenotazioniByUtente] popola selectedPrenotazioni con le prenotazioni dell utente
     * @param key_utente chiave dell'utente
     * @return se non ci sono errori popola [selectedPrenotazioni] altrimenti setta una mutableList vuota
     */
    fun getPrenotazioniByUtente(key_utente: String) {
        queryPrenotazioniByUtente(key_utente).get().addOnSuccessListener {
            it.toObjects<Prenotazione>()
            _selectedPrenotazioni.value = it.toObjects<Prenotazione>().toMutableList()
        }.addOnFailureListener {
            _selectedPrenotazioni.value = mutableListOf()
        }
    }

}