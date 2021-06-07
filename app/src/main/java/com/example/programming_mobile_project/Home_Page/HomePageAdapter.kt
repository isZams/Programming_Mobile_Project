package com.example.programming_mobile_project.Home_Page

import android.util.Log
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.example.programming_mobile_project.database.ChaletDB
import com.example.programming_mobile_project.models.Chalet
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestoreException

val chaletDB = ChaletDB()
val query = chaletDB.queryChalet()

class HomePageAdapter(lifecycleOwner: LifecycleOwner) :
    FirestoreRecyclerAdapter<Chalet, HomePageHolder>(
        FirestoreRecyclerOptions.Builder<Chalet>()
            .setQuery(query, Chalet::class.java)
            .setLifecycleOwner(lifecycleOwner)
            .build()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageHolder {
        return HomePageHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HomePageHolder, position: Int, model: Chalet) {
        holder.binding.CardTitle.text = model.nome_chalet
        holder.binding.CardInd.text = model.indirizzo
        holder.binding.CardDesc.text = model.descrizione
        //Glide.with().load(model.locandina).into(holder.binding.CardImgView)
    }

    override fun onError(e: FirebaseFirestoreException) {
        Log.e("HomePageAdapter error", e.message.toString())
    }

}