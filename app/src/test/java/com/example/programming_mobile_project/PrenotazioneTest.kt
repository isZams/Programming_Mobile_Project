package com.example.programming_mobile_project

import com.example.programming_mobile_project.models.Prenotazione
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PrenotazioneTest {

    @Test
    fun timeStampToString() {
        val prenotazione = Prenotazione()
        assertEquals("01/01/1970", prenotazione.timeStampToString(0))
        assertEquals("12/06/2021", prenotazione.timeStampToString(1623514388000))
    }
}