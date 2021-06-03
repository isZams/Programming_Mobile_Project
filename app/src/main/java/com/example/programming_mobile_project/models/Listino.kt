package com.example.programming_mobile_project.models

data class Listino(
    val prezzo_lettini: Float,
    val prezzo_ombrelloni: Float,
    val prezzo_sdraio: Float,
    val presso_sedie: Float
) {
    constructor() : this(0f, 0f, 0f, 0f)
}
