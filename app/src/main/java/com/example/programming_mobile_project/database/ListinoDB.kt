package com.example.programming_mobile_project.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.programming_mobile_project.models.Listino
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await


class ListinoDB : FirebaseDB() {
    val listinoRef = db.collection("listini")

    private val _selectedListino = MutableLiveData<Listino>()
    val selectedListino: LiveData<Listino>
        get() = _selectedListino


    /** Aggiunge l'oggetto Listino al database
     * @param listino Lo chalet da aggiungere
     * @param chaletKey chalet a cui appartiene il listino
     * @return Se il listino viene correttamente caricato "success" altrimenti null
     */
    suspend fun setListino(chaletKey: String, listino: Listino): String? {
        return try {
            listinoRef
                .document(chaletKey)
                .set(listino)
                .await()
            "success"
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Dato l'id dello chalet ritorna il listino dello chalet corrispondente
     * @param chaletKey key dello chalet
     * @return il listino altrimenti null
     */
    suspend fun getListino(chaletKey: String): Listino? {
        return try {
            val listino = listinoRef
                .document(chaletKey)
                .get()
                .await()
            listino.toObject()
        } catch (e: Exception) {
            null
        }
    }
}