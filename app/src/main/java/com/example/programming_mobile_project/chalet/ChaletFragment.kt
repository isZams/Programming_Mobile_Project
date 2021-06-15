package com.example.programming_mobile_project.chalet

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.programming_mobile_project.R
import com.example.programming_mobile_project.database.ChaletDB
import com.example.programming_mobile_project.databinding.ChaletFragmentBinding
import com.google.android.gms.location.*
import kotlinx.coroutines.launch


class ChaletFragment : Fragment() {
    val args: ChaletFragmentArgs by navArgs()

    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var binding: ChaletFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mFusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this.requireContext())
        binding = DataBindingUtil.inflate(inflater, R.layout.chalet_fragment, container, false)

        binding.prenotaGiornaliero.setOnClickListener {
            goToMappaOmbrelloni(1)
        }

        binding.prenotaMensile.setOnClickListener {
            goToMappaOmbrelloni(30)
        }




        return binding.root
    }

    fun goToMappaOmbrelloni(giorni: Int) {
        findNavController().navigate(
            ChaletFragmentDirections.actionChaletFragmentToBeachFragment(
                args.chaletId,
                giorni
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this)
            .load(R.drawable.loading)
            .into(binding.immagineChalet)
        var destination = ""
        val chaletId = args.chaletId
        val chaletDB = ChaletDB()
        lifecycleScope.launch {
            val chalet = chaletDB.getChalet(chaletId)
            chalet?.let {
                binding.nomeChalet.text = chalet.nome_chalet
                binding.indirizzoChalet.text = chalet.indirizzo
                binding.descrizioneChalet.text = chalet.descrizione
                binding.totLettini.text = chalet.tot_lettini.toString()
                binding.totOmbrelloni.text = chalet.tot_ombrelloni.toString()
                binding.totSdraio.text = chalet.tot_sdraio.toString()
                binding.totSedie.text = chalet.tot_sedie.toString()
                Glide.with(requireContext()).load(chalet.locandina).into(binding.immagineChalet)
                destination = chalet.indirizzo
            }
        }

        val btnMaps: Button = binding.bottoneMaps
        // al click del bottone chiama la funzione displayTrack
        btnMaps.setOnClickListener {
            DisplayTrack(getLastLocation(), destination)
        }
    }


    /**
     * Dopo aver passato i due paremtri, viene lanciato un intent con cui apre GoogleMaps, msotrando
     * il percorso per raggiungere la destinazione
     * Se non è presente un'app sul telefono per aprirla viene lanciato un intent con cui scaricare
     * l'applicazione da PlayStore.
     * @param start Prende come valore la posizione corrente dell'utente
     * @param end Prende come valore la posizione dello chalet
     *
     */
    fun DisplayTrack(start: String, end: String) {
        try {
            val uri: Uri =
                Uri.parse("https://www.google.com/maps/dir/?api=1&origin=" + start + "&destination=" + end)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

        } catch (e: ActivityNotFoundException) {
            val uri: Uri =
                Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this.requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Granted. Start getting the location information
            }
        }
    }

    fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


    /**
     * Prende il valore dell'ultima posizione dell'utente
     * @return startPoint ovvero l'ultima posizione registrata del dispositivo
     */
    @SuppressLint("MissingPermission")
    private fun getLastLocation(): String {
        var startPoint = ""
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this.requireActivity()) { task ->
                    var location: Location = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        startPoint = getAddress(location.latitude, location.longitude)

                    }
                }
            } else {
                Toast.makeText(this.requireContext(), "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
        return startPoint
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this.requireContext())
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            getAddress(mLastLocation.latitude, mLastLocation.longitude)
        }
    }


    /**
     * Prende come parametri latitudine e longitudine della posizione e li converte in un indirizzo
     * @param lat
     * @param lng
     */
    private fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(this.requireContext())
        val list = geocoder.getFromLocation(lat, lng, 1)
        return list[0].getAddressLine(0)
    }


}