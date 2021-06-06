package com.example.programming_mobile_project.elenco_prenotazioni

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.programming_mobile_project.databinding.ItemPrenotazioneBinding

class PrenotazioniHolder private constructor(val binding: ItemPrenotazioneBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup): PrenotazioniHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemPrenotazioneBinding.inflate(layoutInflater, parent, false)
            return PrenotazioniHolder(binding)
        }
    }
}