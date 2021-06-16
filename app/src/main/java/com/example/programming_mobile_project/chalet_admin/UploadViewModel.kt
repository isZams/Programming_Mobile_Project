package com.example.programming_mobile_project.chalet_admin

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programming_mobile_project.database.ChaletDB
import com.example.programming_mobile_project.models.Chalet
import kotlinx.coroutines.*

class UploadViewModel : ViewModel() {

    private val uri: Uri = "".toUri()
    private lateinit var image: Uri
    private var imageSet = false

    /**
     * Inserisce localmente l'immagine per il successivo invio al database
     * @param image immagine che viene salvata localmente di tipo Uri
     */
    fun storeImage(image: Uri) {
        this.image = image
        imageSet = true
    }

    /**
     * Funzione che serve al fragment per caricare il nuovo chalet nel database.
     * Carica in due fasi lo chalet e la sua immagine nello storage sfruttando l'id dello chalet autogenerato da Firebase
     * @param chalet lo chalet che verrà caricato nel database
     */
    fun upload(chalet: Chalet) {
        viewModelScope.launch {
            val db = ChaletDB()
            val id = db.addChalet(chalet)
            if(imageSet == true){
                db.uploadImage(image, id)
            }
        }
        //TODO prendere l'url generato da firebase dell'immagine per metterlo dentro alla locandina
    }
}