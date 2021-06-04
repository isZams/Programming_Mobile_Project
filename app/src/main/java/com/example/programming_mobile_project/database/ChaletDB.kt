package com.example.programming_mobile_project.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.programming_mobile_project.chalet_admin.Chalet

//  https://stackoverflow.com/a/63889166
//   ^ Le coroutine non sono compatibili con firebase

class ChaletDB : FirebaseDB() {

    val _selectedChalet = MutableLiveData<Chalet>()
    val selectedChalet: LiveData<Chalet>
        get() = _selectedChalet


    /**
     * Dato l'id dello chalet ritorna le info dello chalet corrispondente
     * @param chaletKey key dello chalet
     * Il valore ottenuto dalla query viene inserito nel LiveData selectedChalet
     */
    fun getChalet(chaletKey: String) {
        db.reference
            .child("chalets")
            .child(chaletKey)
            .child("info")
            .get().addOnSuccessListener {
                Log.i("firebasee", it.child("descrizione").value.toString())
                // getValue trasforma l'oggetto datasnapshot (it) in un oggetto della classe indicata
                _selectedChalet.value = it.getValue(Chalet::class.java)
            }
    }


    /** Aggiunge l'oggeto Chalet al database
     * @param chalet Lo chalet da aggiungere
     */
    fun addChalet(chalet: Chalet) {
        db.reference.child("chalets").push().child("info").setValue(chalet).addOnFailureListener {
            Log.i("firebasee", "Errore ${it}")
        }
    }
}