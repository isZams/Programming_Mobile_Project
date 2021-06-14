package com.example.programming_mobile_project.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.programming_mobile_project.MainActivity
import com.example.programming_mobile_project.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class LoginAuthFragment : Fragment() {
    private lateinit var model: AuthViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        model = AuthViewModel()
        /**
         * Controlla all'avvio dell'applicazione se l'utente sia già autenticato
         * quindi termina la LoginActivity e avvia la MainActivity
         */
        if (model.isLoggedIn()) {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        return inflater.inflate(R.layout.login_interface, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val txtUsername = view.findViewById<TextInputEditText>(R.id.textUsername)
        val txtPassword = view.findViewById<TextInputEditText>(R.id.textPassword)
        val btnAccedi = view.findViewById<Button>(R.id.btnAccedi)
        val txtRegistrati = view.findViewById<TextView>(R.id.signIn)

        var pw: String
        var email: String

        btnAccedi.setOnClickListener {
            email = txtUsername.text.toString().trim()
            pw = txtPassword.text.toString().trim()
            if (email.isNotEmpty() && pw.isNotEmpty()) {
                lifecycleScope.launch {
                    if (model.sigIn(email, pw) == null) {
                        Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    } else {
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                }
            }
        }

        txtRegistrati.setOnClickListener() {
            view.findNavController().navigate(R.id.action_loginAuthFragment2_to_authFragment)
        }

    }
}