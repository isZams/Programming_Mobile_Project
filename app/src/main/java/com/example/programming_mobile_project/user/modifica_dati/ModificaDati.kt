package com.example.programming_mobile_project.user.modifica_dati

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.programming_mobile_project.R
import com.example.programming_mobile_project.databinding.ModificaDatiFragmentBinding

/**
Fragment della pagina modifica dati utente dove vengono visualizzati i dati dell'utente con la possibilità di modificarli
 */
class ModificaDati : Fragment() {
    private lateinit var binding: ModificaDatiFragmentBinding
    private val viewmodel : ModificaDatiViewModel by viewModels()

    /**
    effettua l'inflate del layout modifica_dati_fragment
     */
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
        val observer = Observer<Boolean> {
            if(it) {
                Toast.makeText(context, "Modifiche effettuate con successo", Toast.LENGTH_LONG).show()
                findNavController().popBackStack(R.id.HomePage, false)
            }
            else Toast.makeText(context, "C'è stato qualche errore", Toast.LENGTH_LONG).show()
        }
        viewmodel.success.observe(viewLifecycleOwner, observer)

        /**
         * Alla pressione del bottone di modifica si controlla se è presente la connesione internet.
         * Nel caso sia connesso allora tenta la modifica dei dati personali. Nel caso ci sia un errore allora
         * verrà mostrato un Toast che informa l'utente che c'è stato qualche errore
         */
        binding.modificaDati.setOnClickListener(){
            val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
            if(isConnected) viewmodel.inviaModifiche(binding.nome.text.toString(), binding.cognome.text.toString())
            else Toast.makeText(context, "Non sei connesso", Toast.LENGTH_LONG).show()
        }

        binding.modificaPassword.setOnClickListener(){
            viewmodel.resetPassword()
            Toast.makeText(context, "Controlla la tua mail per resettare la password", Toast.LENGTH_LONG).show()
        }
    }
}