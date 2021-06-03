package com.example.programming_mobile_project.chalet_admin

import android.R.attr
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.programming_mobile_project.R
import com.google.android.material.textfield.TextInputLayout


class UploadFragment : Fragment() {

    private lateinit var viewModel: UploadViewModel
    val Image_Request_Code = 100;
    private lateinit var imageView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chalet_admin, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UploadViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnCarica: Button = view.findViewById(R.id.btnPubblica)
        val insertTitle: TextInputLayout = view.findViewById(R.id.txtChaletTitle)
        val insertInd: TextInputLayout = view.findViewById(R.id.txtIndirizzo)
        val insertDesc: TextInputLayout = view.findViewById(R.id.txtDesc)
        imageView = view.findViewById<ImageView>(R.id.imgLocandina)
        val btnImg = view.findViewById<Button>(R.id.btnUploadImg)

        btnImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, Image_Request_Code)
        }

        btnCarica.setOnClickListener {
            viewModel.upload()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Image_Request_Code){
            //imageView.setImageURI(data?.data)
        }

    }
}