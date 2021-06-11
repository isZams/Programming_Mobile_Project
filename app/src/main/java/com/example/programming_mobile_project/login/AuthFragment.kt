package com.example.programming_mobile_project.login

import android.app.Application
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.programming_mobile_project.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthFragment: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.google_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = AuthViewModel(view.context)
        val txtUsername = view.findViewById<TextInputEditText>(R.id.textUsername)
        val txtPassword = view.findViewById<TextInputEditText>(R.id.textPassword)
        val btnRegistrati = view.findViewById<Button>(R.id.btnRegistrati)
        val txtgoToLogin = view.findViewById<TextView>(R.id.login)

        var pw: String
        var email: String

        btnRegistrati.setOnClickListener(){
            //model.firebaseEmailSignup(email, pw)
            email = txtPassword.text.toString().trim()
            pw = txtUsername.text.toString().trim()
            model.signUp(email, pw)
            view.findNavController().navigate(R.id.action_authFragment_to_homePage)
        }

        txtgoToLogin.setOnClickListener(){
            view.findNavController().navigate(R.id.action_AuthFragment_to_loginAuthFragment)
        }
    }

}