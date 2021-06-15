package com.example.programming_mobile_project.elenco_prenotazioni

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.programming_mobile_project.R
import com.example.programming_mobile_project.databinding.ElencoPrenotazioniFragmentBinding

class ElencoPrenotazioni : Fragment() {

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

        binding.rvElencoPrenotazioni.adapter = PrenotazioniAdapter(viewLifecycleOwner, findNavController())

        (activity as AppCompatActivity).supportActionBar?.title = "Elenco prenotazioni"

        return binding.root
    }
}