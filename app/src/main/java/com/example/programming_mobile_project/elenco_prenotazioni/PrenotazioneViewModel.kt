package com.example.programming_mobile_project.elenco_prenotazioni

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programming_mobile_project.database.PrenotazioneDB
import com.example.programming_mobile_project.models.Prenotazione
import kotlinx.coroutines.launch

class PrenotazioneViewModel : ViewModel() {
    val prenotazioneDB = PrenotazioneDB()

    private val _prenotazione = MutableLiveData<Prenotazione>()
    val prenotazione: LiveData<Prenotazione>
        get() = _prenotazione

    fun getPrenotazione(key_prenotazione:String){
        viewModelScope.launch {
            _prenotazione.value = prenotazioneDB.getPrenotazione(key_prenotazione)
        }
    }


}