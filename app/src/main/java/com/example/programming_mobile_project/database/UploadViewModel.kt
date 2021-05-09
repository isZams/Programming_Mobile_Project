package com.example.programming_mobile_project.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.net.UrlQuerySanitizer
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.example.programming_mobile_project.R
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URL


class UploadViewModel : ViewModel() {
    private var database = Firebase.database("https://book-beach-default-rtdb.europe-west1.firebasedatabase.app/").reference
    private val storage = Firebase.storage
    fun upload(){
        val id = 0
        val nome = "G7"
        val indirizzo = "Lungomare de Citanò"
        val chalet: Chalet = Chalet(id, nome, indirizzo)
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