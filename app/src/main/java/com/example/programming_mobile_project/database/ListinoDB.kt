package com.example.programming_mobile_project.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.programming_mobile_project.models.Listino


class ListinoDB : FirebaseDB() {
    val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    val _listino = MutableLiveData<Listino>()
    val listino: LiveData<Listino>
        get() = _listino

    /**
     * Crea/modifica la sezione "listino" in "chalets", impostando i prezzi
     * @param listino oggetto di tipo Listino con i prezzi
     * @param chaletKey chalet a cui si riferisce
     * @return valorizza response (success | fail)
     */
    fun setListino(chaletKey: String, listino: Listino) {
        db.reference
            .child("chalets")
            .child(chaletKey)
            .child("listino")
            .setValue(listino)
            .addOnSuccessListener {
                _response.value = "success"
            }
            .addOnFailureListener {
                _response.value = "fail \n$it"
            }
    }

    /**
     * Prende la sezione "listino"
     * @param chaletKey chalet a cui si riferisce
     * @return se l'operazione termina con successo valorizza LiveData listino altrimenti scrive l'errore in response
     */
    fun getListino(chaletKey: String) {
        db.reference
            .child("chalets")
            .child(chaletKey)
            .child("listino")
            .get().addOnSuccessListener {
                _listino.value = it.getValue(Listino::class.java)
            }
            .addOnFailureListener {
                _response.value = "fail \n$it"
            }

    }
}