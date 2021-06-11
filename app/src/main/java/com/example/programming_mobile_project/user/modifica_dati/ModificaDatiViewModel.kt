package com.example.programming_mobile_project.user.modifica_dati

import android.util.Log
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

class ModificaDatiViewModel: ViewModel() {

    private val _user = MutableLiveData(Utente())
    val user: LiveData<Utente>
        get() = _user
    private val utenteDB = UtenteDB()

    fun getUser(){
        viewModelScope.launch() {
            _user.value = utenteDB.getUtente()
            Log.d("Deb", "Utente recuperato: " + user.value?.nome)
        }
    }

    fun inviaModifiche(nome: String, cognome: String){
        viewModelScope.launch(Dispatchers.IO) {
            utenteDB.modificaUtente(Utente(nome, cognome))
        }
    }

}