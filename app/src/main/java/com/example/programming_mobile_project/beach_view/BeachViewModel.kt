package com.example.programming_mobile_project.beach_view


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programming_mobile_project.database.ContatoreDB
import com.example.programming_mobile_project.database.ListinoDB
import com.example.programming_mobile_project.database.PrenotazioneDB
import com.example.programming_mobile_project.models.Contatore
import com.example.programming_mobile_project.models.Listino
import com.example.programming_mobile_project.models.Prenotazione
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import java.io.File

class BeachViewModel : ViewModel() {
    private val prenotazioneDB = PrenotazioneDB()
    private val listinoDB = ListinoDB()
    private val contatoreDB = ContatoreDB()

    private val mapSrcReference = Firebase.storage.reference.child("mappa_ombrelloni")

    //dimensione max in byte del file html
    val htmlRefSize: Long = 2000

    private val _htmlSource = MutableLiveData<String>()
    val htmlSource: LiveData<String>
        get() = _htmlSource

    private val _svgDownloaded = MutableLiveData<String>()
    val svgDownloaded: LiveData<String>
        get() = _svgDownloaded

    private val _ombrello = MutableLiveData<Int>()
    val ombrello: LiveData<Int>
        get() = _ombrello

    private val _ombrelloniOccupati = MutableLiveData<List<Int>?>()
    val ombrelloniOccupati: LiveData<List<Int>?>
        get() = _ombrelloniOccupati

    private val _listino = MutableLiveData<Listino?>()
    val listino: LiveData<Listino?>
        get() = _listino

    private val _contatore = MutableLiveData<Contatore?>()
    val contatore: LiveData<Contatore?>
        get() = _contatore

    private val _idPrenotazione = MutableLiveData<String?>()
    val idPrenotazione: LiveData<String?>
        get() = _idPrenotazione

    /**
     * lambda function necessaria per poter essere passata al costruttore di MapJSInterface
     * per chiamare il metodo nel viewModel
     */
    val selectedOmbrelloChanged: (String) -> Unit =
        {
            val numeroOmbrellone = it.split("_")[1].toInt()
            _ombrello.value = numeroOmbrellone
            _ombrello.value = numeroOmbrellone
        }


    /**
     * Fornisce al fragment la lista degli ombrelloni occupati
     * @return la lista degli ombrelloni occupati oppure null
     */
    fun getOccupati(key_chalet: String) {
        viewModelScope.launch {
            _ombrelloniOccupati.value = prenotazioneDB.getOmbrelloniOccupati(key_chalet)
        }
    }

    // https://firebase.google.com/docs/storage/android/download-files#download_in_memory
    /**
     * Scarica in memoria i byte relativi al file html che viene usato nella webview per visualizzare
     * la mappa degli ombrelloni
     * @return nel LiveData [htmlSource] viene passata la stringa del file altrimenti la stringa
     * "fail" in caso di errore
     */
    fun getHtmlSrc() {
        // reference al file html in storage
        val htmlRef = mapSrcReference.child("html_src").child("map.html")

        htmlRef.getBytes(htmlRefSize)
            .addOnSuccessListener {
                _htmlSource.value = String(it)
            }
            .addOnFailureListener {
                _htmlSource.value = "fail"
            }

    }

    // https://firebase.google.com/docs/storage/android/download-files#download_to_a_local_file
    /**
     * Scarica la mappa svg dello chalet di chiave [chaletKey] in locale
     * @return al termine dell'operazione setta [svgDownloaded] a true altrimenti a false in caso di errore
     */
    fun getSvg(chaletKey: String) {
        val svgRef = mapSrcReference
            .child("svg_chalet")
            .child("$chaletKey.svg")
        val localFile = File.createTempFile(chaletKey, null)
        svgRef.getFile(localFile)
            .addOnSuccessListener {
                _svgDownloaded.value = localFile.path
            }
            .addOnFailureListener {
                _svgDownloaded.value = "fail"
            }
    }

    fun loadListino(chaletKey: String) {
        viewModelScope.launch {
            _listino.value = listinoDB.getListino(chaletKey)
        }
    }

    fun loadContatori(chaletKey: String) {
        viewModelScope.launch {
            _contatore.value = contatoreDB.getContatore(chaletKey)
        }
    }

    fun prenota(prenotazione: Prenotazione) {
        viewModelScope.launch {
            _idPrenotazione.value = prenotazioneDB.setPrenotazione(prenotazione)
            Log.i("ditto", _idPrenotazione.value.toString())
        }
    }
}