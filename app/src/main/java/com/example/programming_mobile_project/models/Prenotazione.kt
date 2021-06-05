package com.example.programming_mobile_project.models

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

/**
 * @param data_termine_prenotazione rappresenta il numero di millisecondi a partire da gennaio 1, 1970, 00:00:00 GMT ( https://docs.oracle.com/javase/7/docs/api/java/util/Date.html#getTime() )
 * @param num_sdraie il numero delle sdraie richieste
 * @param num_lettini il numero dei lettini richiesti
 * @param num_sedie il numero di sedie richieste
 * @param n_ombrellone il numero dell'ombrellone da prenotare
 */
class Prenotazione(
    val data_termine_prenotazione: Long,
    val timestamp_prenotazione: Long,
    val n_ombrellone: Int,
    val num_sdraie: Int,
    val num_sedie: Int,
    val num_lettini: Int,
    val key_utente: String,
    val key_chalet: String,
) {
    constructor() : this(0, 0, 0, 0, 0, 0, "", "")

    fun timeStampToString(timestamp: Long): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN)
            .format(Date(timestamp))
    }

}

