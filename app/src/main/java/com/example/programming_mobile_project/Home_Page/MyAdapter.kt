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
import com.example.programming_mobile_project.chalet_admin.Chalet

class MyAdapter(val context: Context, val list: ArrayList<Chalet>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //that contains the layout for an individual item in the list
        val txtTitle: TextView
        val txtIndirizzo: TextView
        val txtDescrizione: TextView
        val imgProfilo: ImageView

        init {
            txtTitle = itemView.findViewById<TextView>(R.id.CardTitle)
            txtIndirizzo = itemView.findViewById<TextView>(R.id.CardInd)
            txtDescrizione = itemView.findViewById<TextView>(R.id.CardDesc)
            imgProfilo = itemView.findViewById<ImageView>(R.id.CardImgView)
        }

    }

    // Create new views (invoked by the layout manager)  //The method creates and initializes the ViewHolder
    // Create a new view, which defines the UI of the list item
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(context).inflate(R.layout.card_view, viewGroup, false)
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener{
            //qua ci andrà messo cosa dovrà fare l'elemento della lista al click dell'utente
        }
        return holder
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val chalet: Chalet = list.get(position)
        //val currentItem = list[position]
        viewHolder.txtTitle.text = chalet.nome_chalet
        viewHolder.txtIndirizzo.text = chalet.indirizzo
        viewHolder.txtDescrizione.text = chalet.descrizione
        Glide.with(context).load(chalet.locandina).into(viewHolder.imgProfilo)

    }

    override fun getItemCount() = list.size
}