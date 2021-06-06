package com.example.programming_mobile_project.elenco_prenotazioni

import android.util.Log
import android.view.ViewGroup
import com.example.programming_mobile_project.database.PrenotazioneDB
import com.example.programming_mobile_project.models.Prenotazione
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestoreException


val query = PrenotazioneDB().queryPrenotazioniByUtente("key_utente")
val options = FirestoreRecyclerOptions.Builder<Prenotazione>()
    .setQuery(query, Prenotazione::class.java)
    .build()

class PrenotazioniAdapter :
    FirestoreRecyclerAdapter<Prenotazione, PrenotazioniHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): PrenotazioniHolder {
        // Create a new instance of the ViewHolder, in this case we are using a custom
        // layout called R.layout.message for each item
        return PrenotazioniHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PrenotazioniHolder, position: Int, model: Prenotazione) {
        holder.binding.txtViewDataTermine.text = model.data_termine_prenotazione.toString()
        Log.i("firebasee", "model ${model.data_termine_prenotazione.toString()}")
        holder.binding.txtViewOmbrellone.text = model.n_ombrellone.toString()
    }



    override fun onError(e: FirebaseFirestoreException) {
        Log.e("firebasee", e.message.toString())
    }

    override fun onDataChanged() {
        Log.i("firebasee", "data changed")
    }


}