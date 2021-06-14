package com.example.programming_mobile_project.user.modifica_dati

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programming_mobile_project.database.UtenteDB
import com.example.programming_mobile_project.models.Utente
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
view model che gestisce la logica per la form di modifica dati utente
 */
class ModificaDatiViewModel: ViewModel() {

    private val _user = MutableLiveData(Utente())
    val user: LiveData<Utente>
        get() = _user
    private val utenteDB = UtenteDB()

    /**
    funzione per prelevare le informazioni dell'utente specifico e popolare la LiveData
    vieModelScope fa parte di CoroutineScope e viene utilizzato per lanciare le coroutine all'interno del viewModel
     */
    fun getUser(){
        viewModelScope.launch() {
            _user.value = utenteDB.getUtente()
            Log.d("Deb", "Utente recuperato: " + user.value?.nome)
        }
    }

    /**
    * @param nome dell'utente loggato
    * @param cognome dell'utente loggato
    lancio della funzione per la modifica delle informazioni dell'utente
     */
    fun inviaModifiche(nome: String, cognome: String){
        viewModelScope.launch(Dispatchers.IO) {
            utenteDB.modificaUtente(Utente(nome, cognome))
        }
    }
    /**
    funzione per il reset della password dell'utente loggato
     */
    fun resetPassword(){
        utenteDB.resetPassword()
    }

}