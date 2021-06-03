package com.example.programming_mobile_project.Home_Page

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class HomePageViewModel: ViewModel(){
    private var database = Firebase.database("https://book-beach-default-rtdb.europe-west1.firebasedatabase.app/").reference

    fun receive(){
        database.child("chalets").child("0").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

}