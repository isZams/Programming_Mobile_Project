package com.example.programming_mobile_project.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.programming_mobile_project.models.Listino
import com.google.firebase.firestore.ktx.toObject


class ListinoDB : FirebaseDB() {
    val listinoRef = db.collection("listini")

    private val _selectedListino = MutableLiveData<Listino>()
    val selectedListino: LiveData<Listino>
        get() = _selectedListino


    /** Aggiunge l'oggetto Listino al database
     * @param listino Lo chalet da aggiungere
     * @param chaletKey chalet a cui appartiene il listino
     * @return Se il listino viene correttamente caricato in selectedListino viene inserito listino altrimenti Listino()
     */
    fun setListino(chaletKey: String, listino: Listino) {
        listinoRef
            .document(chaletKey)
            .set(listino)
            .addOnSuccessListener {
                _selectedListino.value = listino
            }
            .addOnFailureListener {
                _selectedListino.value = Listino()
            }
    }

    /**
     * Dato l'id dello chalet ritorna il listino dello chalet corrispondente
     * @param chaletKey key dello chalet
     * @return Il valore ottenuto dalla query viene inserito nel LiveData selectedListino. Se non viene trovato ritorna Listino()
     */
    fun getListino(chaletKey: String) {
        listinoRef
            .document(chaletKey)
            .get().addOnSuccessListener {
                // toObject trasforma l'oggetto DocumentSnapshot! (it) in un oggetto della classe indicata
                _selectedListino.value = it.toObject<Listino>()
            }
            .addOnFailureListener { _selectedListino.value = Listino() }
    }


//    /**
//     * Crea/modifica la sezione "listino" in "chalets", impostando i prezzi
//     * @param listino oggetto di tipo Listino con i prezzi
//     * @param chaletKey chalet a cui si riferisce
//     * @return valorizza response (success | fail)
//     */
//    fun setListino(chaletKey: String, listino: Listino) {
//        db.reference
//            .child("chalets")
//            .child(chaletKey)
//            .child("listino")
//            .setValue(listino)
//            .addOnSuccessListener {
//                _response.value = "success"
//            }
//            .addOnFailureListener {
//                _response.value = "fail \n$it"
//            }
//    }
//
//    /**
//     * Prende la sezione "listino"
//     * @param chaletKey chalet a cui si riferisce
//     * @return se l'operazione termina con successo valorizza LiveData listino altrimenti scrive l'errore in response
//     */
//    fun getListino(chaletKey: String) {
//        db.reference
//            .child("chalets")
//            .child(chaletKey)
//            .child("listino")
//            .get().addOnSuccessListener {
//                _listino.value = it.getValue(Listino::class.java)
//            }
//            .addOnFailureListener {
//                _response.value = "fail \n$it"
//            }
//
//    }
}