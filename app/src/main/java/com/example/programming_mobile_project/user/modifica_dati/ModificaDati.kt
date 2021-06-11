package com.example.programming_mobile_project.user.modifica_dati

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.programming_mobile_project.R
import com.example.programming_mobile_project.databinding.ModificaDatiFragmentBinding
import com.google.android.material.snackbar.Snackbar


class ModificaDati : Fragment() {
    private lateinit var binding: ModificaDatiFragmentBinding
    private val viewmodel : ModificaDatiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.modifica_dati_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewmodel
        binding.lifecycleOwner = this
        viewmodel.getUser()
        binding.modificaDati.setOnClickListener(){
            viewmodel.inviaModifiche(binding.nome.text.toString(), binding.cognome.text.toString())
        }
        binding.modificaPassword.setOnClickListener(){
            viewmodel.resetPassword()
            Toast.makeText(context, "Controlla la tua mail per resettare la password", Toast.LENGTH_LONG).show()
        }
    }
}