import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.programming_mobile_project.R

import com.example.programming_mobile_project.database.ChaletDB
import com.example.programming_mobile_project.databinding.ChaletFragmentBinding
import com.example.programming_mobile_project.models.Chalet
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class ChaletFragment : Fragment() {
    val args: ChaletFragmentArgs by navArgs()
    private lateinit var binding: ChaletFragmentBinding
       val PERMISSION_ID = 42
      lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.chalet_fragment, container, false)

        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.chalet_fragment)
        
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this)
            .load(R.drawable.loading)
            .into(binding.immagineChalet)
        var destination: String = ""
        val chaletId = args.chaletId
        val chaletDB = ChaletDB()
        chaletDB.getChalet(chaletId)
        val chaletObserver = Observer<Chalet> { chalet ->
            Glide.with(this)
                .load(chalet.locandina)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(binding.immagineChalet)
            binding.nomeChalet.text = chalet.nome_chalet
            binding.indirizzoChalet.text = chalet.indirizzo
            binding.descrizioneChalet.text = chalet.descrizione
            binding.totLettini.text = chalet.tot_lettini.toString()
            binding.totOmbrelloni.text = chalet.tot_ombrelloni.toString()
            binding.totSdraio.text = chalet.tot_sdraio.toString()
            binding.totSedie.text = chalet.tot_sedie.toString()
            destination = chalet.indirizzo.trim()
        }

        chaletDB.selectedChalet.observe(viewLifecycleOwner, chaletObserver)


        val startPoint: String = "null".trim()
        val btnMaps: Button = binding.bottoneMaps
        // al click del bottone chiama la funzione displayTraci
        btnMaps.setOnClickListener(View.OnClickListener {
            fun onClick(v: View) {
                DisplayTrack(startPoint, destination)
            }
        })
    }

    fun DisplayTrack(start: String, end: String) {
        try {
            //passa il punto di partenza e quello di arrivo alla stringa che verrà caricata in google maps
            val uri: Uri = Uri.parse("https://www.google.co.in/maps/dir" + start + "/" + end)
            val intent: Intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

        } catch  (ActivityNotFoundException e) {
            //se non ci sono app per aprire maps viene rimandato al playstore
            val uri: Uri =
                Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps")
            val intent: Intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

    }


}