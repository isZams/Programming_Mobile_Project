package com.example.programming_mobile_project.elenco_prenotazioni

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.programming_mobile_project.R
import com.example.programming_mobile_project.database.ChaletDB
import com.example.programming_mobile_project.models.Prenotazione
import com.example.programming_mobile_project.database.PrenotazioneDB
import com.example.programming_mobile_project.databinding.PrenotazioneFragmentBinding
import com.example.programming_mobile_project.models.Chalet
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.ServerValue

class Prenotazione : Fragment() {
    val args: PrenotazioneArgs by navArgs()

    private lateinit var binding: PrenotazioneFragmentBinding
    private val prenotazioneDB = PrenotazioneDB()
    private val chaletDB = ChaletDB()

    private val observerChalet = Observer<Chalet> {
        (activity as AppCompatActivity).supportActionBar?.title = it.nome_chalet
    }

    private val observerPrenotazione = Observer<Prenotazione> {
        binding.txtLettini.text = it.num_lettini.toString()
        binding.txtOmbrellone.text = it.n_ombrellone.toString()
        binding.txtSdraio.text = it.num_sdraie.toString()
        binding.txtSedie.text = it.num_sedie.toString()
        binding.txtDataEffettuata.text = it.timeStampToString(it.timestamp_prenotazione)
        binding.txtTerminePrenotazione.text = it.timeStampToString(it.data_termine_prenotazione)

        chaletDB.selectedChalet.observe(viewLifecycleOwner, observerChalet)
        chaletDB.getChalet(it.key_chalet)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.prenotazione_fragment,
            container,
            false
        )

        prenotazioneDB.selectedPrenotazione.observe(viewLifecycleOwner, observerPrenotazione)
        prenotazioneDB.getPrenotazione(args.keyPrenotazione)

        return binding.root
    }

    fun showQRDialog(key_prenotazione: String){
//        context?.let {
//            MaterialAlertDialogBuilder(it)
//                .setIcon()
//        }
    }
}