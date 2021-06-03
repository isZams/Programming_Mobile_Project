package com.example.programming_mobile_project.database

import android.util.Log
import com.example.programming_mobile_project.chalet_admin.Chalet
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

open class FirebaseDB {
    val db: FirebaseDatabase = Firebase.database("https://book-beach-default-rtdb.europe-west1.firebasedatabase.app/")
}