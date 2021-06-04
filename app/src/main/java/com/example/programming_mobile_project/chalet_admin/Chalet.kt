package com.example.programming_mobile_project.chalet_admin

data class Chalet(
    val locandina: String,
    val nome_chalet: String,
    val indirizzo: String,
    val descrizione: String,
    val tot_sedie: Int,
    val tot_ombrelloni: Int,
    val tot_sdraio: Int,
    val tot_lettini: Int
) {
    constructor() : this("", "", "", "", -1, -1, -1, -1)
}