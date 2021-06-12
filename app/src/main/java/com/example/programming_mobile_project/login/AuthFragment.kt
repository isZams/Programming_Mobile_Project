package com.example.programming_mobile_project.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.programming_mobile_project.R
import com.example.programming_mobile_project.models.Utente
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class AuthFragment : Fragment() {

    private lateinit var utente: Utente

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.google_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = AuthViewModel()
        val txtUsername = view.findViewById<EditText>(R.id.textUsername)
        val txtPassword = view.findViewById<TextInputEditText>(R.id.textPassword)
        val txtNome = view.findViewById<TextInputEditText>(R.id.textNome)
        val txtCognome = view.findViewById<TextInputEditText>(R.id.textCognome)
        val btnRegistrati = view.findViewById<Button>(R.id.btnRegistrati)
        val txtgoToLogin = view.findViewById<TextView>(R.id.login)

        var pw: String
        var email: String
        var nome: String
        var cognome: String

        btnRegistrati.setOnClickListener() {
            nome = txtNome.text.toString()
            cognome = txtCognome.text.toString()
            email = txtUsername.text.toString().trim()
            pw = txtPassword.text.toString().trim()

            if (nome.isNotEmpty() && cognome.isNotEmpty() && email.isNotEmpty() && pw.isNotEmpty()) {
                lifecycleScope.launch {
                    if (model.signUp(email, pw) == null) {
                        Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    } else {
                        utente = Utente(nome, cognome)
                        model.addAuthUserOnDB(utente)
                        view.findNavController().navigate(R.id.action_loginAuthFragment_to_HomePage)
                    }
                }
            }
        }

        txtgoToLogin.setOnClickListener() {
            view.findNavController().navigate(R.id.action_AuthFragment_to_loginAuthFragment)
        }
    }

}