package com.example.programming_mobile_project.Home_Page

import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.programming_mobile_project.R
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MyAdapter(private val dataSet: Array<String>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private var database = Firebase.database.reference
    private var databaseRefChild = database.child("chalets")
    private val storage = Firebase.storage.reference

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //that contains the layout for an individual item in the list
        val txtTitle = view.findViewById<TextView>(R.id.CardTitle)
        var txtIndirizzo = view.findViewById<TextView>(R.id.CardInd)
        var txtDescrizione = view.findViewById<TextView>(R.id.CardDesc)
        var imgProfilo = view.findViewById<ImageView>(R.id.CardImgView)

    }


    // Create new views (invoked by the layout manager)  //The method creates and initializes the ViewHolder
    // Create a new view, which defines the UI of the list item
    //R.layout_row_item è da sostituire con il layout del singolo elemento della lista che sarà creato con card del material design
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_view, viewGroup, false)
        return ViewHolder(layout)
    }



    // Replace the contents of a view (invoked by the layout manager)  //this method to associate a ViewHolder with data
    // Get element from your dataset at this position and replace the contents of the view with that element
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val nomeChalet = databaseRefChild.child(position.toString()).child("nome_chalet").get().toString()
        val indirizzoChalet = databaseRefChild.child(position.toString()).child("indirizzo").get().toString()
        val descChalet = databaseRefChild.child(position.toString()).child("descrizione").get().toString()
        viewHolder.txtTitle.text = nomeChalet
        viewHolder.txtIndirizzo.text = indirizzoChalet
        viewHolder.txtDescrizione.text = descChalet
        Glide.with(context).load(viewHolder.storage).into(viewHolder.imgProfilo)

        viewHolder.itemView.setOnClickListener{
            //qua ci andrà messo cosa dovrà fare l'elemento della lista al click dell'utente
            var position: Int = getAdapterPosition()
            val context = view.context
            val intent = Intent(context, Stabilimento::class.java).apply {
                putExtra("IMMAGINE", "uri dell'immagine")
                putExtra("TITOLO", txtTitle.text)
                putExtra("DESCRIZIONE", txtDescrizione.text)
                putExtra("DISTANZA", txtIndirizzo.text)
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}