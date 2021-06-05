package com.example.programming_mobile_project.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.programming_mobile_project.models.Contatore
import com.google.firebase.database.ServerValue
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.toObject

class ContatoreDB : FirebaseDB() {
    val contatoreRef = db.collection("contatori")

    val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    val _selectedContatore = MutableLiveData<Contatore>()
    val selectedContatore: LiveData<Contatore>
        get() = _selectedContatore


    /**
     * Imposta il contatore di uno chalet
     * @return se operazione conclusa con successo [response] = "success" altrimenti [response] = "fail ..."
     */
    fun setContatore(chaletKey: String, contatore: Contatore) {
        contatoreRef
            .document(chaletKey)
            .set(contatore)
            .addOnSuccessListener {
                _response.value = "success"
            }
            .addOnFailureListener {
                _response.value = "fail $it"
            }
    }

    /**
     * Prende il contatore di uno chalet
     * @return se operazione conclusa con successo setta [selectedContatore] altrimenti [selectedContatore] viene valorizzato col costruttore vuoto Contatore()
     */
    fun getContatore(chaletKey: String) {
        contatoreRef
            .document(chaletKey)
            .get()
            .addOnSuccessListener {
                _selectedContatore.value = it.toObject<Contatore>()
            }
            .addOnFailureListener {
                _selectedContatore.value = Contatore()
            }
    }

    /**
     * Applica un delta pari al contatore dei lettini
     * @param value delta (positivo o negativo) applicato al contatore. Di default vale 1
     * @return setta [response] ("success" oppure "fail...")
     */
    fun incrementLettini(chaletKey: String, value: Long = 1) {
        contatoreRef
            .document(chaletKey)
            .update("current_lettini", FieldValue.increment(value))
            .addOnSuccessListener {
                _response.value = "success"
            }
            .addOnFailureListener {
                _response.value = "fail $it"
            }
    }

    /**
     * Applica un delta pari al contatore delle sedie
     * @param value delta (positivo o negativo) applicato al contatore. Di default vale 1
     * @return setta [response] ("success" oppure "fail...")
     */
    fun incrementSedie(chaletKey: String, value: Long = 1) {
        contatoreRef
            .document(chaletKey)
            .update("current_sedie", FieldValue.increment(value))
            .addOnSuccessListener {
                _response.value = "success"
            }
            .addOnFailureListener {
                _response.value = "fail $it"
            }
    }

    /**
     * Applica un delta pari al contatore delle sdraie
     * @param value delta (positivo o negativo) applicato al contatore. Di default vale 1
     * @return setta [response] ("success" oppure "fail...")
     */
    fun incrementSdraie(chaletKey: String, value: Long = 1) {
        contatoreRef
            .document(chaletKey)
            .update("current_sdraie", FieldValue.increment(value))
            .addOnSuccessListener {
                _response.value = "success"
            }
            .addOnFailureListener {
                _response.value = "fail $it"
            }
    }
}