package com.example.programming_mobile_project.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.programming_mobile_project.chalet_admin.Chalet

class ChaletDB : FirebaseDB() {

    val _selectedChalet = MutableLiveData<Chalet>()
    val selectedChalet: LiveData<Chalet>
        get() = _selectedChalet


    /**
     * Dato l'id dello chalet ritorna lo chalet corrispondente
     * @param chaletKey key dello chalet
     *
     */
    fun getChalet(chaletKey: String) {
        db.getReference().child("chalets").child(chaletKey).get().addOnSuccessListener {
            Log.i("firebasee", it.child("descrizione").value.toString())
            _selectedChalet.value = Chalet(
                it.child("locandina").value.toString(),
                it.child("nome_chalet").value.toString(),
                it.child("indirizzo").value.toString(),
                it.child("descrizione").value.toString(),
            )
        }
    }

    fun addChalet(chalet: Chalet) {
        db.getReference().child("chalets").push().setValue(chalet).addOnFailureListener {
            Log.i("firebasee", "Errore ${it}")
        }
    }
}