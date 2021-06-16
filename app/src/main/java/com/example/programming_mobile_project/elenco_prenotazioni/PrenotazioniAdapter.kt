package com.example.programming_mobile_project.elenco_prenotazioni

import android.util.Log
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.programming_mobile_project.database.ChaletDB
import com.example.programming_mobile_project.database.PrenotazioneDB
import com.example.programming_mobile_project.login.AuthViewModel
import com.example.programming_mobile_project.models.Prenotazione
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.launch

val prenotazioneDB = PrenotazioneDB()

val query = prenotazioneDB.queryPrenotazioniByUtente(AuthViewModel().uid()!!)

/**
 * Implementa l'adapter per il fragment che mostra l'elenco delle prenotazioni effettuate da un utente.
 * @param lifecycleOwner dev'essere passato dal fragment che istanzia l'oggetto
 */
class PrenotazioniAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val navController: NavController
) :
    FirestoreRecyclerAdapter<Prenotazione, PrenotazioniHolder>(
        FirestoreRecyclerOptions.Builder<Prenotazione>()
            .setQuery(query, Prenotazione::class.java)
            .setLifecycleOwner(lifecycleOwner)
            .build()
    ) {

    private val chaletDB = ChaletDB()

    override
    fun onCreateViewHolder(parent: ViewGroup, i: Int): PrenotazioniHolder {
        // Create a new instance of the ViewHolder, in this case we are using a custom
        // layout called R.layout.message for each item
        return PrenotazioniHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PrenotazioniHolder, position: Int, model: Prenotazione) {
        holder.binding.txtViewDataTermine.text =
            model.timeStampToString(model.timestamp_prenotazione)
        holder.binding.txtViewOmbrellone.text = model.n_ombrellone.toString()
        val action = ElencoPrenotazioniDirections.actionElencoPrenotazioniToPrenotazione(
            snapshots.getSnapshot(position).id
        )

        holder.binding.btnPrenotazione.setOnClickListener {
            navController.navigate(action)
        }



        lifecycleOwner.lifecycleScope.launch {
            val chalet = chaletDB.getChalet(model.key_chalet)
            if (chalet != null) {
                holder.binding.nomeChalet.text = chalet.nome_chalet
            }
        }
    }

    override fun onError(e: FirebaseFirestoreException) {
        Log.e("PrenotazioneAdapter error", e.message.toString())
    }
}