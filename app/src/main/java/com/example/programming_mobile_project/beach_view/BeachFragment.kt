// Fonte: https://medium.com/@scode43/interactive-svg-image-in-android-app-using-kotlin-and-javascript-6715c16397bb
package com.example.programming_mobile_project.beach_view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.programming_mobile_project.R
import com.example.programming_mobile_project.databinding.BeachMapBinding
import com.example.programming_mobile_project.login.AuthViewModel
import com.example.programming_mobile_project.models.Contatore
import com.example.programming_mobile_project.models.Listino
import com.example.programming_mobile_project.models.Prenotazione
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.lang.NumberFormatException
import java.time.LocalDate
import java.time.ZoneOffset

/**
 * Al caricamento del fragment viene lanciato viewModel.getSvg. Nell'observer relativo a getSvg,
 * al termine dell'elaborazione, viene lanciato viewModel.getOccupati. Nell'observer relativo a
 * getOccupati, al termine delle operazioni, viene lanciato l'ultimo metodo della catena: viewModel.getHtml.
 * Questo scarica il documento html a cui viene integrata la mappa svg.
 * All'onPageFinished della webview viene lanciato il metodo javascript initListener che rende gli
 * ombrelloni liberi cliccabili e blocca quelli occupati
 */
class BeachFragment : Fragment() {
    private lateinit var binding: BeachMapBinding
    private val args: BeachFragmentArgs by navArgs()

    private lateinit var chaletKey: String
    val viewModel: BeachViewModel by viewModels()
    val ombrelloniOccupati = mutableListOf<Int>()

    companion object {
        const val JAVASCRIPT_OBJ = "Android"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.beach_map, container, false)

        setUpWebLayout()

        binding.showExtra.setOnClickListener {
            binding.extra.visibility =
                if (binding.extra.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        chaletKey = args.chaletKey

        var pathSvg = ""
        var currentListino = Listino()

        val listinoObserver = Observer<Listino?> {
            if (it != null) {
                currentListino = it
                binding.extra.findViewById<TextView>(R.id.ombrello_prezzo).text =
                    "${currentListino.prezzo_ombrelloni} €"
                viewModel.loadContatori(chaletKey)
            }

        }

        val contatoreObserver = Observer<Contatore?> {
            if (it != null) {
                setUpBtnListener(it, currentListino)
            }
        }

        val svgObserver = Observer<String> {
            if (it != "fail") {
                pathSvg = it
                viewModel.getOccupati(args.chaletKey)
            } else {
                Log.e("error", "svg non trovata")
            }
        }

        val occupatiObserver = Observer<List<Int>?> {
            if (it != null) {
                ombrelloniOccupati.addAll(it)
                viewModel.getHtmlSrc()
            }
        }

        val htmlObserver = Observer<String> {
            val svgFile = File(pathSvg)
            val svgText = svgFile.readText()
            svgFile.delete()
            val document = it.replace("svgString", svgText)
            binding.webView.loadDataWithBaseURL(
                "",
                document,
                "text/html",
                "UTF-8",
                null
            )
            binding.mapProgressBar.visibility = View.GONE
            binding.beachMapView.visibility = View.VISIBLE
        }

        val idObserver = Observer<String?> {
            if (it == null) {
                Toast.makeText(context, "Errore nella prenotazione", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                val action = BeachFragmentDirections.actionBeachFragmentToPrenotazione(it)
                Toast.makeText(context, "Prenotazione effettuata con successo", Toast.LENGTH_LONG)
                    .show()
                findNavController().navigate(action)
            }
        }


        // Aggancio gli observer ai LiveData
        viewModel.svgDownloaded.observe(viewLifecycleOwner, svgObserver)
        viewModel.ombrelloniOccupati.observe(viewLifecycleOwner, occupatiObserver)
        viewModel.htmlSource.observe(viewLifecycleOwner, htmlObserver)
        viewModel.listino.observe(viewLifecycleOwner, listinoObserver)
        viewModel.contatore.observe(viewLifecycleOwner, contatoreObserver)
        viewModel.idPrenotazione.observe(viewLifecycleOwner, idObserver)

        viewModel.getSvg(chaletKey)
        viewModel.loadListino(chaletKey)

        val ombrelloSelectedObserver = Observer<Int> {
            binding.extra.findViewById<TextView>(R.id.n_ombrello_selected).text = it.toString()
        }
        viewModel.ombrello.observe(viewLifecycleOwner, ombrelloSelectedObserver)

        binding.prenotaOmbrellone.setOnClickListener {
            if (binding.extra.findViewById<TextView>(R.id.n_ombrello_selected).text.toString() == "") {
                view?.let { view ->
                    Snackbar.make(
                        view,
                        "Seleziona prima un ombrellone",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            } else {
                showDialogAcquisto(
                    binding.extra.findViewById<TextView>(R.id.n_ombrello_selected).text.toString(),
                    currentListino.prezzo_ombrelloni,
                    binding.extra.findViewById<TextView>(R.id.editLettini).text.toString(),
                    binding.extra.findViewById<TextView>(R.id.editSdraie).text.toString(),
                    binding.extra.findViewById<TextView>(R.id.editSedie).text.toString(),
                )
            }
        }

        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun setUpWebLayout() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.builtInZoomControls = true
        binding.webView.settings.displayZoomControls = false
        binding.webView.settings.useWideViewPort = true
        binding.webView.addJavascriptInterface(
            MapJSInterface(viewModel.selectedOmbrelloChanged),
            JAVASCRIPT_OBJ
        )

        /**
         * Al termine del caricamento del documento nella webview viene chiamato il metodo initLitener
         * che prende gli ombrelloniOccupati e in base a questi imposta il listener al click solo
         * agli ombrelloni interessati
         */
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                binding.webView.loadUrl(
                    "javascript:initListener('${
                        ombrelloniOccupati.joinToString(
                            "--"
                        )
                    }')"
                )
            }
        }
    }

    fun setUpBtnListener(contatore: Contatore, listino: Listino) {
        fun setEditText(editText: EditText, txtView: TextView, rimanenti: Int, prezzo: Float) {
            editText.filters = arrayOf<InputFilter>(MinMaxFilter(0, rimanenti))
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    try {
                        txtView.text =
                            (s.toString().toInt() * prezzo).toString()
                    } catch (e: NumberFormatException) {
                        txtView.text = ""
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }

        val editLettini = binding.extra.findViewById<EditText>(R.id.editLettini)
        val editSdraie = binding.extra.findViewById<EditText>(R.id.editSdraie)
        val editSedie = binding.extra.findViewById<EditText>(R.id.editSedie)
        val prezzoLettini = binding.extra.findViewById<TextView>(R.id.prezzo_tot_lettini)
        val prezzoSdraie = binding.extra.findViewById<TextView>(R.id.prezzo_tot_sdraie)
        val prezzoSedie = binding.extra.findViewById<TextView>(R.id.prezzo_tot_sedie)

        setEditText(editLettini, prezzoLettini, contatore.current_lettini, listino.prezzo_lettini)
        setEditText(editSdraie, prezzoSdraie, contatore.current_sdraie, listino.prezzo_sdraio)
        setEditText(editSedie, prezzoSedie, contatore.current_sedie, listino.prezzo_sedie)


    }

    fun showDialogAcquisto(
        nOmbr: String,
        prezzoOmbrellone: Float,
        n_lettini: String,
        n_sdraie: String,
        n_sedie: String,
    ) {
        var resoconto = " \nOmbrellone: $nOmbr"
        resoconto += if (n_lettini == "0" || n_lettini == "") "" else " \nLettini : $n_lettini"
        resoconto += if (n_sedie == "0" || n_sedie == "") "" else " \nSedie : $n_sedie"
        resoconto += if (n_sdraie == "0" || n_sdraie == "") "" else " \nSdraie : $n_sdraie"

        if (context != null) {
            MaterialAlertDialogBuilder(
                requireContext(),
                R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered
            )
                .setMessage(
                    resources.getString(R.string.messaggio_conferma_prenotazione) + resoconto
                )
                .setNegativeButton(resources.getString(R.string.decline)) { _, _ ->
                    // Negativo
                }
                .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                    makePrenotazione(prezzoOmbrellone)
                }
                .show()
        }
    }

    fun makePrenotazione(prezzoOmbrellone: Float) {
        binding.beachMapView.visibility = View.GONE
        binding.extra.visibility = View.GONE
        binding.mapProgressBar.visibility = View.VISIBLE


        var totale = prezzoOmbrellone
        totale += binding.extra.findViewById<TextView>(R.id.prezzo_tot_sdraie).text.toString()
            .toFloatOrNull() ?: 0f
        totale += binding.extra.findViewById<TextView>(R.id.prezzo_tot_lettini).text.toString()
            .toFloatOrNull() ?: 0f
        totale += binding.extra.findViewById<TextView>(R.id.prezzo_tot_sedie).text.toString()
            .toFloatOrNull() ?: 0f


        // DateTime di oggi con ora 00:00:00
        val oggi = LocalDate.now().atStartOfDay(ZoneOffset.systemDefault())
        viewModel.prenota(
            Prenotazione(
                oggi.plusDays(args.durata.toLong()).toInstant().toEpochMilli(),
                System.currentTimeMillis(),
                binding.extra.findViewById<TextView>(R.id.n_ombrello_selected).text.toString()
                    .toInt(),
                binding.extra.findViewById<TextView>(R.id.editSdraie).text.toString().toIntOrNull()
                    ?: 0,
                binding.extra.findViewById<TextView>(R.id.editSedie).text.toString().toIntOrNull()
                    ?: 0,
                binding.extra.findViewById<TextView>(R.id.editLettini).text.toString().toIntOrNull()
                    ?: 0,
                AuthViewModel().uid()!!,
                chaletKey,
                totale,
            )
        )
    }

    override fun onDestroy() {
        binding.webView.removeJavascriptInterface(JAVASCRIPT_OBJ)
        super.onDestroy()
    }
}