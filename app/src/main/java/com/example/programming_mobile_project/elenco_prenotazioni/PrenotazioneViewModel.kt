package com.example.programming_mobile_project.elenco_prenotazioni

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programming_mobile_project.database.ChaletDB
import com.example.programming_mobile_project.database.PrenotazioneDB
import com.example.programming_mobile_project.models.Prenotazione
import kotlinx.coroutines.launch

class PrenotazioneViewModel : ViewModel() {
    val prenotazioneDB = PrenotazioneDB()
    val chaletDB = ChaletDB()

    private val _prenotazione = MutableLiveData<Prenotazione>()
    val prenotazione: LiveData<Prenotazione>
        get() = _prenotazione

    private val _nomeChalet = MutableLiveData<String>()
    val nomeChalet: LiveData<String>
        get() = _nomeChalet

    fun getPrenotazione(key_prenotazione: String) {
        viewModelScope.launch {
            _prenotazione.value = prenotazioneDB.getPrenotazione(key_prenotazione)
        }
    }

    fun getNomeChalet(key_chalet: String) {
        viewModelScope.launch {
            val chalet = chaletDB.getChalet(key_chalet)
            if (chalet != null) {
                _nomeChalet.value = chalet.nome_chalet
            }
        }
    }
}