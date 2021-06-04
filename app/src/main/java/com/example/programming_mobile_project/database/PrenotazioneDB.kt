package com.example.programming_mobile_project.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.programming_mobile_project.models.Prenotazione

class PrenotazioneDB : FirebaseDB() {

    val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    val _prenotazioni = MutableLiveData<MutableList<Prenotazione>>()
    val prenotazioni: LiveData<MutableList<Prenotazione>>
        get() = _prenotazioni


    /**
     *  Controlla se l'ombrellone è gia occupato e successivamente effettua la prenotazione
     *  @param chaletKey su quale chalet applicare la prenotazione
     *  @return il risultato viene scritto in response: `ombrellone prenotato` oppure `fail prenotazione ombrellone` oppure `ombrellone gia occupato`
     */
    fun setPrenotazione(chaletKey: String, prenotazione: Prenotazione) {
        //Safe check che n_ombrellone è davvero libero
        db.reference
            .child("chalets")
            .child(chaletKey)
            .child("prenotazione")
            .child(prenotazione.n_ombrellone.toString())
            .get().addOnSuccessListener {
                if (it.getValue() === null) {
                    //ombrellone non è occupato
                    db.reference
                        .child("chalets")
                        .child(chaletKey)
                        .child("prenotazione")
                        .child(prenotazione.n_ombrellone.toString())
                        .setValue(prenotazione)
                        .addOnSuccessListener {
                            _response.value = "ombrellone prenotato"
                        }
                        .addOnFailureListener {
                            _response.value = "fail prenotazione ombrellone"
                        }

                } else {
                    _response.value = "ombrellone gia occupato"
                }
            }
    }


    fun getPrenotazioniByUtente(key_utente: String) {
        db.reference
            .child("chalets")

                //prenotazioni.value.addAll(listOf(it.getValue()))
    }

}