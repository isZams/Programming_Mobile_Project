package com.example.programming_mobile_project.Home_Page

import android.util.Log
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.programming_mobile_project.database.ChaletDB
import com.example.programming_mobile_project.models.Chalet
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestoreException



/**
* Implementa l'adapter per il fragment che mostra l'elenco degli chalet.
* @param lifecycleOwner di LifecyclerOwner che permette di dichiarare un lifecycle
* @param NavController di NavController per implementare la navigazione tra fragment
* chaleDB oggetto della classe chaletDB
* query richiama il metodo queryChalet che prende tutte le info di ogni chalet
*/

val chaletDB = ChaletDB()
val query = chaletDB.queryChalet()

class HomePageAdapter(lifecycleOwner: LifecycleOwner, private val navController: NavController) :
    FirestoreRecyclerAdapter<Chalet, HomePageHolder>(
        FirestoreRecyclerOptions.Builder<Chalet>()
            .setQuery(query, Chalet::class.java)
            .setLifecycleOwner(lifecycleOwner)
            .build()
    ) {
    //onCreateViewHolder utilizzato per l'inflate del layout riferito all'item della recyclerview
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageHolder {
        return HomePageHolder.from(parent)
    }

    /**
    * @param position per capire la riga di riferimento della lista
    * @param model oggetto della data class Chalet da cui andare a prendere i dati
    * @holder viewHolder che descrive com'è composto l'item della lista e la sua posizione all'interno di RecyclerView.
    * onBindViewHolder viene utilizzato per mostrare i dati dell' item alla posizione specifica
    * se l'utente clicca sull'item, viene trasportato sulla pagina specifica dello stabilimento
     */
    override fun onBindViewHolder(holder: HomePageHolder, position: Int, model: Chalet) {
        holder.binding.CardTitle.text = model.nome_chalet
        holder.binding.CardInd.text = model.indirizzo
        holder.binding.CardDesc.text = model.descrizione
        Glide.with(holder.itemView.context).load(model.locandina).into(holder.binding.CardImgView)          //viene utilizzata la libreria Glide per andare a prendere l'immagine dello chalet specifico

        holder.itemView.setOnClickListener {
            val id = snapshots.getSnapshot(position).id
            val action = HomePageDirections.actionHomePageToChaletFragment(id)
            navController.navigate(action)
        }
    }

    /**
    * @param e di FireBaseFirestoreException per gestire le eccezioni di Cloud Storage
     */
    override fun onError(e: FirebaseFirestoreException) {
        Log.e("HomePageAdapter error", e.message.toString())
    }

}