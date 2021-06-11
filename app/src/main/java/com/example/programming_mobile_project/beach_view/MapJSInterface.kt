package com.example.programming_mobile_project.beach_view

import android.webkit.JavascriptInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * La classe implementa il metodo [clickedOmbrello] che viene invocato lato javascript quando un
 * ombrellone viene cliccato. Questo a sua volta invoca [updateOmbrello] che è necessario perche
 * i metodi @JavascriptInteface vengono eseguiti su un non-Main thread. Solitamente l'aggiornamento
 * di un viewModel da un thread non-main viene segnalato con un'eccezione, ma stranamente non in questo caso
 * [Fonte](https://c306.net/thewinterblog/2020/01/22/til-webview-javascriptinterface-methods-run-in-a-different-thread/)
 *
 */
class MapJSInterface(val sendOmbrello: (String) -> Unit) {

    /**
     * Invocato lato javascript quando un ombrellone viene cliccato
     */
    @JavascriptInterface
    fun clickedOmbrello(ombrello: String) {
        updateOmbrello(ombrello)
    }

    /**
     * Necessita di [CoroutineScope] perchè altrimenti [sendOmbrello] viene eseguito su un thread
     * non-main
     */
    private fun updateOmbrello(ombrello: String) {
        CoroutineScope(Dispatchers.Main).launch {
            sendOmbrello(ombrello)
        }
    }
}