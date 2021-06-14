package com.example.programming_mobile_project.user.modifica_dati

import androidx.lifecycle.*
import com.example.programming_mobile_project.database.UtenteDB
import com.example.programming_mobile_project.models.Utente
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModificaDatiViewModel: ViewModel() {

    private val _user = MutableLiveData(Utente())
    val user: LiveData<Utente>
        get() = _user
    private val utenteDB = UtenteDB()
    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean>
        get() = _success

    fun getUser(){
        viewModelScope.launch() {
            _user.value = utenteDB.getUtente()
        }
    }

    /**
     * Metodo che sfrutta la classe UtenteDB per effettuare una modifica nel database del nome e del cognome
     * dell'utente attualmente autenticato. Se la modifica ha successo allora aggiorna il livedata success con true
     * altrimenti se c'è stato un errore mette false
     * @param nome nome modificato
     * @param cognome cognome modificato
     */
    fun inviaModifiche(nome: String, cognome: String){
        viewModelScope.launch(Dispatchers.IO) {
             _success.postValue(utenteDB.modificaUtente(Utente(nome, cognome)))
        }
    }

    fun resetPassword(){
        utenteDB.resetPassword()
    }
}