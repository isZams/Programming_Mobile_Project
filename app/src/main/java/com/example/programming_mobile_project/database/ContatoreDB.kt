package com.example.programming_mobile_project.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.ServerValue

class ContatoreDB : FirebaseDB() {
    val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    /**
     * Applica un delta pari a value al contatore dei lettini
     * @param value delta (positivo o negativo) applicato al contatore. Di default vale 1
     * @return setta response (success | fail)
     */
    fun incrementLettini(chaletKey: String, value: Long = 1) {
        db.reference
            .child("chalets")
            .child(chaletKey)
            .child("contatore")
            .child("current_lettini")
            .setValue(ServerValue.increment(value))
            .addOnSuccessListener {
                _response.value = "success"
            }
            .addOnFailureListener {
                _response.value = "fail \n$it"
            }
    }

    /**
     * Applica un delta pari a value al contatore delle sedie
     * @param value delta (positivo o negativo) applicato al contatore. Di default vale 1
     * @return setta response (success | fail)
     */
    fun incrementSedie(chaletKey: String, value: Long = 1) {
        db.reference
            .child("chalets")
            .child(chaletKey)
            .child("contatore")
            .child("current_sedie")
            .setValue(ServerValue.increment(value))
            .addOnSuccessListener {
                _response.value = "success"
            }
            .addOnFailureListener {
                _response.value = "fail \n$it"
            }
    }

    /**
     * Applica un delta pari a value al contatore delle sdraie
     * @param value delta (positivo o negativo) applicato al contatore. Di default vale 1
     * @return setta response (success | fail)
     */
    fun incrementSdraie(chaletKey: String, value: Long = 1) {
        db.reference
            .child("chalets")
            .child(chaletKey)
            .child("contatore")
            .child("current_sdraie")
            .setValue(ServerValue.increment(value))
            .addOnSuccessListener {
                _response.value = "success"
            }
            .addOnFailureListener {
                _response.value = "fail \n$it"
            }
    }
}