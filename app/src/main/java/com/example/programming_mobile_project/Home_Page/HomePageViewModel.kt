package com.example.programming_mobile_project.Home_Page

import android.app.DownloadManager
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.programming_mobile_project.chalet_admin.Chalet
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.Query
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class HomePageViewModel: ViewModel(){
    private val database = Firebase.database("https://book-beach-default-rtdb.europe-west1.firebasedatabase.app/")
    val ref = database.getReference("chalets")

    fun setUpRecyclerView(){

    }

    fun receiveData(){

    }

}