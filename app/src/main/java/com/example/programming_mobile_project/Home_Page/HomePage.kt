package com.example.programming_mobile_project.Home_Page

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.programming_mobile_project.R
import com.example.programming_mobile_project.chalet_admin.Chalet
import com.example.programming_mobile_project.databinding.ElencoPrenotazioniFragmentBinding
import com.example.programming_mobile_project.databinding.HomePageBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class HomePage: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: HomePageBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.home_page,
                container,
                false
            )
        binding.recyclerview.adapter = HomePageAdapter(viewLifecycleOwner)
        binding.recyclerview.layoutManager = LinearLayoutManager(activity)
        return binding.root
    }

}