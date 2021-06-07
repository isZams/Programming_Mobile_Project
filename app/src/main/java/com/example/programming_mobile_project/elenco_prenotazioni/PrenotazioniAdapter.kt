package com.example.programming_mobile_project.elenco_prenotazioni

import android.util.Log
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.example.programming_mobile_project.database.PrenotazioneDB
import com.example.programming_mobile_project.models.Prenotazione
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestoreException

val prenotazioneDB = PrenotazioneDB()
val query = prenotazioneDB.queryPrenotazioniByUtente("key_utente")

/**
 * Implementa l'adapter per il fragment che mostra l'elenco delle prenotazioni effettuate da un utente.
 * @param lifecycleOwner dev'essere passato dal fragment che istanzia l'oggetto
 */
class PrenotazioniAdapter(lifecycleOwner: LifecycleOwner) :
    FirestoreRecyclerAdapter<Prenotazione, PrenotazioniHolder>(
        FirestoreRecyclerOptions.Builder<Prenotazione>()
            .setQuery(query, Prenotazione::class.java)
            .setLifecycleOwner(lifecycleOwner)
            .build()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): PrenotazioniHolder {
        // Create a new instance of the ViewHolder, in this case we are using a custom
        // layout called R.layout.message for each item
        return PrenotazioniHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PrenotazioniHolder, position: Int, model: Prenotazione) {
        holder.binding.txtViewDataTermine.text = model.data_termine_prenotazione.toString()
        holder.binding.txtViewOmbrellone.text = model.n_ombrellone.toString()
    }

    override fun onError(e: FirebaseFirestoreException) {
        Log.e("PrenotazioneAdapter error", e.message.toString())
    }
}