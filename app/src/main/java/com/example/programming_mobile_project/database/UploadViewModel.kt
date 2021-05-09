package com.example.programming_mobile_project.database

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UploadViewModel : ViewModel() {
    private var database: DatabaseReference = Firebase.database.reference
    fun upload(){
        val id = 0
        val nome = "G7"
        val indirizzo = "Lungomare de Citanò"
        val chalet: Chalet = Chalet(id, nome, indirizzo)
        Log.d("DEB", chalet.id.toString()+ " "+chalet.nome_chalet+" "+chalet.indirizzo)

        database.child("chalets").child(id.toString()).setValue(chalet)
        //database.s

    }

    fun receive(){
        database.child("chalets").child("0").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
}