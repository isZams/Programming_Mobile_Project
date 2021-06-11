package com.example.programming_mobile_project.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.programming_mobile_project.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginAuthFragment: Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_interface, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = AuthViewModel(view.context)
        val txtUsername = view.findViewById<TextInputEditText>(R.id.textUsername)
        val txtPassword = view.findViewById<TextInputEditText>(R.id.textPassword)
        val btnAccedi = view.findViewById<Button>(R.id.btnAccedi)

        var pw: String
        var email: String

        btnAccedi.setOnClickListener(){
            //model.firebaseEmailSigin(email, pw)
            email = txtPassword.text.toString().trim()
            pw = txtUsername.text.toString().trim()
            model.sigIn(email, pw)
            view.findNavController().navigate(R.id.action_loginAuthFragment_to_HomePage)
        }

    }
}