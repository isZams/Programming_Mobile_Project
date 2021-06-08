package com.example.programming_mobile_project.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.programming_mobile_project.models.Chalet
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject


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
     * @return Se lo chalet viene correttamente caricato in selectedChalet viene inserito chalet altrimenti Chalet()
     */
    fun addChalet(chalet: Chalet) {
        chaletRef
            .add(chalet)
            .addOnSuccessListener {
                _selectedChalet.value = chalet
            }
            .addOnFailureListener {
                _selectedChalet.value = Chalet()
            }
    }


//
//    /**
//     * Dato l'id dello chalet ritorna le info dello chalet corrispondente
//     * @param chaletKey key dello chalet
//     * Il valore ottenuto dalla query viene inserito nel LiveData selectedChalet
//     */
//    fun getChalet(chaletKey: String) {
//        db.reference
//            .child("chalets")
//            .child(chaletKey)
//            .child("info")
//            .get().addOnSuccessListener {
//                Log.i("firebasee", it.child("descrizione").value.toString())
//                // getValue trasforma l'oggetto datasnapshot (it) in un oggetto della classe indicata
//                _selectedChalet.value = it.getValue(Chalet::class.java)
//            }
//    }
//
//
//    /** Aggiunge l'oggeto Chalet al database
//     * @param chalet Lo chalet da aggiungere
//     */
//    fun addChalet(chalet: Chalet) {
//        db.reference.child("chalets").push().child("info").setValue(chalet).addOnFailureListener {
//            Log.i("firebasee", "Errore ${it}")
//        }
//    }
}