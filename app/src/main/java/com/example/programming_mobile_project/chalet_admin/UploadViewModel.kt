package com.example.programming_mobile_project.chalet_admin

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


class UploadViewModel(application: Application) : ViewModel() {
    private var database = Firebase.database("https://book-beach-default-rtdb.europe-west1.firebasedatabase.app/").reference
    private val uri: Uri = "".toUri()
    private val context: Context = application.applicationContext

    fun upload(){
        val id = 0
        val locandina = ""
        val nome = "G7"
        val indirizzo = "Lungomare de Citanò"
        val descrizione= ""
        val chalet: Chalet = Chalet(locandina, nome, indirizzo, descrizione)
        Log.d("DEB", chalet.nome_chalet + " " + chalet.indirizzo)

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
    /*
    fun downloadUrl(): StorageReference {
        return storage.getReferenceFromUrl("gs://book-beach.appspot.com/images/Carpa.jpg")
    }
    */
    private fun getFileExtention(uri: Uri): String? {
        val contentResolver: ContentResolver = context.contentResolver
        val mimeTypeMap: MimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }
    fun uploadImage(){
        if(uri != null){
            val storage = Firebase.storage.reference
            val imageRef = storage.child("images").child(System.currentTimeMillis().toString() + "." + getFileExtention(uri))
            imageRef.putFile(uri).addOnCompleteListener(){
                imageRef.downloadUrl.addOnSuccessListener {
                    val url = uri.toString()
                    Log.d("downloadUrl", url)
                    Toast.makeText(context, "image upload success", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}