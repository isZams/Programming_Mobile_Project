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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.programming_mobile_project.R
import com.example.programming_mobile_project.databinding.BeachMapBinding
import com.example.programming_mobile_project.models.Contatore
import com.example.programming_mobile_project.models.Listino
import java.io.File

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
        var currentContatore = Contatore()

        val listinoObserver = Observer<Listino?> {
            if (it != null) {
                currentListino = it
            }
        }

        val contatoreObserver = Observer<Contatore?> {
            if (it != null) {
                currentContatore = it
                setUpBtnListener(it)
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

        val ombrelloSelectedObserver = Observer<Int> {
            Log.i("eccomi", "fewfwfefewfewd")
            binding.extra.findViewById<TextView>(R.id.n_ombrello_selected).text = it.toString()
        }
        viewModel.ombrello.observe(viewLifecycleOwner, ombrelloSelectedObserver)

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

    fun setUpBtnListener(contatore: Contatore) {
        val editLettini = binding.extra.findViewById<EditText>(R.id.editLettini)
        editLettini.filters = arrayOf<InputFilter>(MinMaxFilter(0, contatore.current_lettini))
        editLettini.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }
        }

        )
    }

    override fun onDestroy() {
        binding.webView.removeJavascriptInterface(JAVASCRIPT_OBJ)
        super.onDestroy()
    }
}