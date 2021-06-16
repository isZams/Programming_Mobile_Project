package com.example.programming_mobile_project.database

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.programming_mobile_project.models.Chalet
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await


//  https://stackoverflow.com/a/63889166
//   ^ Le coroutine non sono compatibili con firebase

class ChaletDB : FirebaseDB() {
    val chaletRef = db.collection("chalets")

    val _selectedChalet = MutableLiveData<Chalet>()
    val selectedChalet: LiveData<Chalet>
        get() = _selectedChalet

    /**
     * Dato l'id dello chalet ritorna le info dello chalet corrispondente
     * @param chaletKey key dello chalet
     * @return Il valore ottenuto dalla query viene inserito nel LiveData selectedChalet. Se non viene trovato ritorna Chalet()
     */
    fun getChalet(chaletKey: String) {
        chaletRef
            .document(chaletKey)
            .get().addOnSuccessListener {
                // toObject trasforma l'oggetto DocumentSnapshot! (it) in un oggetto della classe indicata
                _selectedChalet.value = it.toObject<Chalet>()
            }
            .addOnFailureListener { _selectedChalet.value = Chalet() }
    }

    //query che prende tutte le info di ogni chalet
    fun queryChalet(): Query {
        return chaletRef
    }


    /** Aggiunge l'oggetto Chalet al database
     * @param chalet Lo chalet da aggiungere
     * @return l'id dello chalet che è stato inserito. Ritorna null se c'è stato un errore
     */
    suspend fun addChalet(chalet: Chalet): String {
        var id = ""
        try {
            val response = chaletRef.add(chalet).await()
            id = response.id
        } catch (e: Exception) {
        }
        return id
    }

    /** Aggiunge l'oggetto Chalet al database
     * @param image l'immagine da caricare di tipo Uri
     * @param nomeFile il nome con cui caricare l'immagine
     * @return stringa che contiene l'url dell'immagine caricata nel database
     */
    suspend fun uploadImage(image: Uri, nomeFile: String): String {
        var url = ""
        try {
            if (nomeFile != "") {
                val storage = Firebase.storage.reference
                val imageRef = storage.child("images").child(nomeFile)
                imageRef.putFile(image).await()
                imageRef.downloadUrl.addOnSuccessListener {
                    url = it.toString()
                    chaletRef.document(nomeFile).update("locandina", url)
                }
            }
        }finally {
            return url
        }

    }
}