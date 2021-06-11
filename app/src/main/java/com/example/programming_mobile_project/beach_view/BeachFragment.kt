// Fonte: https://medium.com/@scode43/interactive-svg-image-in-android-app-using-kotlin-and-javascript-6715c16397bb
package com.example.programming_mobile_project.beach_view

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.programming_mobile_project.R
import com.example.programming_mobile_project.databinding.BeachMapBinding
import com.example.programming_mobile_project.models.Contatore
import com.example.programming_mobile_project.models.Listino
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.lang.NumberFormatException

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

        chaletKey = "GcZn37MhMnXkIyC0DxnQ"
        var pathSvg = ""
        var currentListino = Listino()

        val listinoObserver = Observer<Listino?> {
            if (it != null) {
                currentListino = it
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
                viewModel.getOccupati(chaletKey)
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
        }

        // Aggancio gli observer ai LiveData
        viewModel.svgDownloaded.observe(viewLifecycleOwner, svgObserver)
        viewModel.ombrelloniOccupati.observe(viewLifecycleOwner, occupatiObserver)
        viewModel.htmlSource.observe(viewLifecycleOwner, htmlObserver)
        viewModel.listino.observe(viewLifecycleOwner, listinoObserver)
        viewModel.contatore.observe(viewLifecycleOwner, contatoreObserver)

        viewModel.getSvg(chaletKey)
        viewModel.loadListino(chaletKey)

        val ombrelloSelectedObserver = Observer<Int> {
            Log.i("eccomi", "fewfwfefewfewd")
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
                    context,
                    binding.extra.findViewById<TextView>(R.id.n_ombrello_selected).text.toString(),
                    binding.extra.findViewById<TextView>(R.id.editLettini).text.toString(),
                    binding.extra.findViewById<TextView>(R.id.editSedie).text.toString(),
                    binding.extra.findViewById<TextView>(R.id.editSdraie).text.toString(),
                )
            }
        }

        //serve per mostrare il bottone nella toolbar
        setHasOptionsMenu(true)

        return binding.root
    }

    // TODO da controllare correttezza di questa toolbar messa prima della toolbar presente in activity
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.beach_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.showExtra -> {
                binding.extra.visibility =
                    if (binding.extra.visibility == View.GONE) View.VISIBLE else View.GONE
            }
        }
        return true
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
        curr_context: Context?,
        numeroOmbrellone: String,
        n_lettini: String,
        n_sedie: String,
        n_sdraie: String
    ) {
        var resoconto = " \nOmbrellone: $numeroOmbrellone"
        resoconto += if (n_lettini == "0" || n_lettini == "") "" else " \nLettini : $n_lettini"
        resoconto += if (n_sedie == "0" || n_sedie == "") "" else " \nSedie : $n_sedie"
        resoconto += if (n_sdraie == "0" || n_sdraie == "") "" else " \nSdraie : $n_sdraie"

        if (curr_context != null) {
            MaterialAlertDialogBuilder(
                curr_context,
                R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered
            )
                .setMessage(
                    resources.getString(R.string.messaggio_conferma_prenotazione) + resoconto
                )
                .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
                    // Negativo
                }
                .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                    // Va avanti
                    Log.i("ditto", "si")
                }
                .show()
        }
    }

    override fun onDestroy() {
        binding.webView.removeJavascriptInterface(JAVASCRIPT_OBJ)
        super.onDestroy()
    }
}