package com.example.programming_mobile_project.login

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.programming_mobile_project.R

class AuthFragment: Fragment(){

    private lateinit var txtUsername: EditText
    private lateinit var txtPassword: EditText
    private lateinit var btnAccedi: Button
    private lateinit var btnRegistrati: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.google_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtUsername = view.findViewById(R.id.textUsername)
        txtPassword = view.findViewById(R.id.textPassword)
        btnAccedi = view.findViewById(R.id.btnAccedi)
        btnRegistrati = view.findViewById(R.id.btnRegistrati)

        var model = ViewModelProvider(this).get(AuthViewModel::class.java)
        var pw:String
        var email:String

        btnAccedi.setOnClickListener(){
            email = txtUsername.text.toString()
            pw = txtPassword.text.toString()
            model.signInWithEmail(email, pw)
            view.findNavController().navigate(R.id.action_authFragment_to_homePage)
        }

        btnRegistrati.setOnClickListener(){
            email = txtUsername.text.toString()
            pw = txtPassword.text.toString()
            model.signUpWithEmail(email, pw)
            view.findNavController().navigate(R.id.action_authFragment_to_homePage)
        }
    }

}