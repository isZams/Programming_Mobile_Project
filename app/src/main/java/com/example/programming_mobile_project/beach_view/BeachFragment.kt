// Fonte: https://medium.com/@scode43/interactive-svg-image-in-android-app-using-kotlin-and-javascript-6715c16397bb
package com.example.programming_mobile_project.beach_view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.programming_mobile_project.R
import com.example.programming_mobile_project.databinding.BeachMapBinding


class BeachFragment : Fragment() {
    private val mapRepo = MapRepo()
    val viewModel: BeachViewModel by viewModels()

    companion object {
        val BASE_URL = "file:///android_asset/"
        val JAVASCRIPT_OBJ = "Android"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: BeachMapBinding =
            DataBindingUtil.inflate(inflater, R.layout.beach_map, container, false)

        setUpWebLayout(binding.webView)

        val file_name = "ombrelloni_balneare.svg"
        val svgString = activity?.assets?.open(file_name)?.bufferedReader()?.readText()

        if (svgString != null) {
            binding.webView.loadDataWithBaseURL(
                BASE_URL,
                mapRepo.getHTMLBody(svgString),
                "text/html",
                "UTF-8",
                null
            )
        }
        return binding.root
    }

    fun setUpWebLayout(webView: WebView) {
        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.settings.useWideViewPort = true
        webView.addJavascriptInterface(
            MapJSInterface(ombrelliChanged),
            JAVASCRIPT_OBJ
        )
    }

    /* lambda function necessaria per poter essere passata al costruttore di MapJSInterface
    * per chiamare il metodo nel viewModel
    */
    val ombrelliChanged: (String) -> Unit = {
        val numeroOmbrellone: Int = it.split("_")[1].toInt()
        viewModel.selectedOmbrelliChanged(numeroOmbrellone)
    }

    override fun onDestroy() {
        view?.findViewById<WebView>(R.id.webView)?.removeJavascriptInterface(JAVASCRIPT_OBJ)
        super.onDestroy()
    }
}