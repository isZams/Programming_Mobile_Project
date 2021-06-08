package com.example.programming_mobile_project.elenco_prenotazioni

import android.app.Dialog
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
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
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

class Prenotazione : Fragment() {

    companion object {
        private const val MAX_BRIGHTNESS = 1f
    }

    private var previousBrightness = MAX_BRIGHTNESS

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

        //genero il qr
        val qr = generaQR(args.keyPrenotazione)


        binding.showQR.setOnClickListener {
            toggleBrightness()
            showQRDialog(qr)
        }

        return binding.root
    }

    /**
     * Dato in input un valore genera il qr ad esso associato
     * @param stringa Il valore che il qr deve rappresentare
     */
    fun generaQR(stringa: String): Bitmap? {
        //Source: https://code.luasoftware.com/tutorials/android/android-generate-qrcode-with-xzing/
        val writer = QRCodeWriter()
        val size = Resources.getSystem().displayMetrics.widthPixels
        val bitMatrix = writer.encode(stringa, BarcodeFormat.QR_CODE, size, size)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }


    /**
     * Genera il dialog per mostrare il QR code
     */
    fun showQRDialog(bitmap: Bitmap?) {
        context?.let {
            val dialog = Dialog(it)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            //necessario per eliminare lo "sporco" dai bordi
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.qr_prenotazione_dialog)
            val closeBtn: Button = dialog.findViewById(R.id.btnCloseQR)
            val img: ImageView = dialog.findViewById(R.id.imgViewQR)
            img.setImageBitmap(bitmap)


            closeBtn.setOnClickListener {
                dialog.dismiss()
                toggleBrightness("min")
            }
            dialog.show()
        }
    }


    /**
     * Varia la luminosita dello schermo per facilitare la lettura del QR code
     * @param flag settato a qualsiasi stringa se si vuole settare allo stato precedente,
     * altrimenti lasciato al valore di default se si vuole massimizzare la luminosità
     */
    fun toggleBrightness(flag: String = "max") {
        if (flag === "max") {
            activity?.let {
                val attributes = it.window.attributes
                previousBrightness = attributes.screenBrightness
                attributes.screenBrightness = MAX_BRIGHTNESS
                it.window.attributes = attributes
            }
        } else {
            activity?.let {
                val attributes = it.window.attributes

                attributes.screenBrightness = previousBrightness
                it.window.attributes = attributes
            }
        }
    }
}