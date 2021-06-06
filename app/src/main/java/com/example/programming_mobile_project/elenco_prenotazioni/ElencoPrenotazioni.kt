package com.example.programming_mobile_project.elenco_prenotazioni

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.programming_mobile_project.R
import com.example.programming_mobile_project.databinding.ElencoPrenotazioniFragmentBinding

class ElencoPrenotazioni : Fragment() {

    var adapter = PrenotazioniAdapter()
    private lateinit var viewModel: ElencoPrenotazioniViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: ElencoPrenotazioniFragmentBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.elenco_prenotazioni_fragment,
                container,
                false
            )


        binding.rvElencoPrenotazioni.adapter = adapter

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}