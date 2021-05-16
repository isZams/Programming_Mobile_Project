package com.example.programming_mobile_project.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.programming_mobile_project.R

class AuthFragment: Fragment(){

    lateinit var txtUsername: EditText
    lateinit var txtPassword: EditText
    lateinit var btnAccedi: Button
    lateinit var btnRegistrati: Button
    lateinit var email: String
    lateinit var pw:String
    lateinit var authModel: AuthViewModel

    //metodo dove va messo solo inflate
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.google_login, container, false)
        return v
    }

    //va sviluppata la logica
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        txtUsername = view.findViewById(R.id.textUsername)
        txtPassword = view.findViewById(R.id.textPassword)
        btnAccedi = view.findViewById(R.id.btnAccedi)
        btnRegistrati = view.findViewById(R.id.btnRegistrati)
        email = txtUsername.text.toString()
        pw = txtPassword.text.toString()

        btnAccedi.setOnClickListener(){
            authModel.signInWithEmail(email, pw)
        }

        btnRegistrati.setOnClickListener(){
            authModel.signUpWithEmail(email, pw)
        }

    }

}