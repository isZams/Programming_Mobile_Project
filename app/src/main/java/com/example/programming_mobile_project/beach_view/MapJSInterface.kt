package com.example.programming_mobile_project.beach_view

import android.webkit.JavascriptInterface

/*
* La classe prende come parametro una funzione che riceve una stringa e non ritorna nulla.
* Noi passeremo una funzione che presa la stringa la trasforma in un intero che poi verrà inserito
* nel Set presente nel viewModel
* */
class MapJSInterface(val sendOmbrello: (String) -> Unit) {

    @JavascriptInterface
    fun clickedOmbrello(ombrello: String) {
        sendOmbrello(ombrello)
    }
}