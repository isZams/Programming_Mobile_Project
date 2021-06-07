package com.example.programming_mobile_project.Home_Page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.programming_mobile_project.databinding.CardViewBinding

class HomePageHolder private constructor(val binding: CardViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup): HomePageHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CardViewBinding.inflate(layoutInflater, parent, false)
            return HomePageHolder(binding)
        }
    }
}