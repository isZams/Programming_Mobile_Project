package com.example.programming_mobile_project.chalet_admin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.programming_mobile_project.R
import com.example.programming_mobile_project.databinding.ChaletAdminBinding
import com.example.programming_mobile_project.models.Chalet


class UploadFragment : Fragment() {

    val viewModel: UploadViewModel by viewModels()

    private lateinit var binding : ChaletAdminBinding
    val Image_Request_Code = 100
    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.chalet_admin, container, false)
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.chalet_admin)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView = binding.imgLocandina

        /**
         * All'interno di questo listener si attiva l'intent che permette di selezionare una immagine dalla propria galleria
         * e lo salva localmente prima di caricare tutte le informazioni nel database
         */
        binding.btnCaricaImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/"
            startActivityForResult(intent, Image_Request_Code)
        }

        /**
         * All'interno di questo listener viene creata una istanza di Chalet che viene passata al viewModel il quale
         * caricherà nel database le informazioni inserite dall'utente.
         */
        binding.btnPubblica.setOnClickListener {
            val chalet = Chalet("",
                binding.txtChaletTitle.text.toString(),
                binding.txtIndirizzo.text.toString(),
                binding.txtDesc.text.toString(),
                if(binding.txtTotSedie.text == null) binding.txtTotSedie.text.toString().toInt() else 0,
                if(binding.txtTotOmbrelloni.text == null) binding.txtTotOmbrelloni.text.toString().toInt() else 0,
                if(binding.txtTotSdraie.text == null) binding.txtTotSdraie.text.toString().toInt() else 0,
                if(binding.txtTotLettini.text == null) binding.txtTotLettini.text.toString().toInt() else 0
            )
            viewModel.upload(chalet)
        }
    }

    /**
     * Metodo responsabile di attivare l'intent per la selezione dell'immagine e che poi inserisce l'immagine selezionata dentro
     * una ImageView per la visualizzazione temporanea dell'utente
     * e la salva dentro al viewModel
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Image_Request_Code){
            val image = data?.data
            Glide.with(this)
                .load(image)
                .error(R.drawable.error)
                .into(imageView)
            if (image != null) {
                viewModel.storeImage(image)
            }
        }
    }
}