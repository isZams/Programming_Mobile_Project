package com.example.programming_mobile_project.Home_Page

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.programming_mobile_project.R

class MyAdapter(private val dataSet: Array<String> ): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //that contains the layout for an individual item in the list

        lateinit var txtTitle: TextView
        lateinit var txtDistanza: TextView
        lateinit var txtDescrizione: TextView
        lateinit var imgProfilo: ImageView

        init {
            // Define click listener for the ViewHolder's View.
            txtTitle = view.findViewById(R.id.CardTitle)
            txtDistanza= view.findViewById(R.id.CardDist)
            txtDescrizione = view.findViewById(R.id.CardDesc)
            imgProfilo = view.findViewById(R.id.CardImgView)
            /*
            view.setOnClickListener {
                //qua ci andrà messo cosa dovrà fare l'elemento della lista al click dell'utente
                val context = view.context
                val intent = Intent(context, Stabilimento::class.java).apply {
                    putExtra("IMMAGINE", "uri dell'immagine")
                    putExtra("TITOLO", txtTitle.text)
                    putExtra("DESCRIZIONE", txtDescrizione.text)
                    putExtra("DISTANZA", txtDistanza.text)
                }
            }*/
        }

    }

    // Create new views (invoked by the layout manager)  //The method creates and initializes the ViewHolder
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_view, viewGroup, false)  //R.layout_row_item è da sostituire con il layout del singolo elemento della lista che sarà creato con card del material design
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)  //this method to associate a ViewHolder with data
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the contents of the view with that element

       /* viewHolder.textView.text = dataSet[position] */
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}