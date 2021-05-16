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

    private lateinit var txtUsername: EditText
    private lateinit var txtPassword: EditText
    private lateinit var btnAccedi: Button
    private lateinit var btnRegistrati: Button
    private lateinit var email: String
    private lateinit var pw:String
    private lateinit var authModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.google_login, container, false)
        txtUsername = v.findViewById(R.id.textUsername)
        txtPassword = v.findViewById(R.id.textPassword)
        btnAccedi = v.findViewById(R.id.btnAccedi)
        btnRegistrati = v.findViewById(R.id.btnRegistrati)


        btnAccedi.setOnClickListener(){
            email = txtUsername.text.toString()
            pw = txtPassword.text.toString()
            authModel.signInWithEmail(email, pw)
        }

        btnRegistrati.setOnClickListener(){
            email = txtUsername.text.toString()
            pw = txtPassword.text.toString()
            authModel.signUpWithEmail(email, pw)
        }
        return v
    }

}