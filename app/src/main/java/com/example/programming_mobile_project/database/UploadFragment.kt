package com.example.programming_mobile_project.database

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.programming_mobile_project.R
import com.google.android.material.textfield.TextInputLayout

class UploadFragment : Fragment() {

    private lateinit var viewModel: UploadViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.upload_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UploadViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val button: Button = view.findViewById(R.id.button)
        val insert: TextInputLayout = view.findViewById(R.id.textInputLayout)
        val textView: TextView = view.findViewById(R.id.textView)
        button.setOnClickListener {
            viewModel.upload()

        }
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val caricaButton = view.findViewById<Button>(R.id.button2)
        caricaButton.setOnClickListener {
            Glide.with(view).load(viewModel.downloadUrl()).into(imageView)
        }
    }
}