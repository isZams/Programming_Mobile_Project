package com.example.programming_mobile_project.Home_Page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.programming_mobile_project.R
import com.example.programming_mobile_project.databinding.HomePageBinding

/**
Fragment della HomePage dove vengono visualizzati tutti gli chalet prensenti nel database
 */
class HomePage : Fragment() {

    /**
     * OnCreateView utilizza il databinding per l'inflate del layout home_page e per l'inizializzazione della recyclerView
     * @return binding.root layout associato con il binding
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: HomePageBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.home_page,
                container,
                false
            )
        binding.recyclerview.adapter = HomePageAdapter(viewLifecycleOwner, findNavController())
        binding.recyclerview.layoutManager = LinearLayoutManager(activity)
        return binding.root
    }

}