package com.example.programming_mobile_project.chalet_admin

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


class UploadViewModel : ViewModel() {
    private var database = Firebase.database("https://book-beach-default-rtdb.europe-west1.firebasedatabase.app/").reference
    private val storage = Firebase.storage

    fun upload(){
        val id = 0
        val locandina = ""
        val nome = "G7"
        val indirizzo = "Lungomare de Citanò"
        val descrizione= ""
        val chalet: Chalet = Chalet(id, locandina, nome, indirizzo, descrizione)
        Log.d("DEB", chalet.id.toString() + " " + chalet.nome_chalet + " " + chalet.indirizzo)

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

    fun simpleUpload(){
        val db = Firebase.database("https://book-beach-default-rtdb.europe-west1.firebasedatabase.app/")
        val myRef = db.getReference("message")
        Log.d("DEB", "FIREBASE REFERENCED")
        myRef.setValue("Hello, World!")
    }

    fun downloadUrl(): StorageReference {
        return storage.getReferenceFromUrl("gs://book-beach.appspot.com/images/Carpa.jpg")
    }

}