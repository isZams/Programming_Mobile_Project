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


class HomePage : Fragment() {

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