package com.example.programming_mobile_project.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.programming_mobile_project.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginAuthFragment : Fragment() {
    private lateinit var model: AuthViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        model = AuthViewModel()

        if (model.isLoggedIn()) {
            findNavController().navigate(LoginAuthFragmentDirections.actionLoginAuthFragmentToHomePage())
        }
        return inflater.inflate(R.layout.login_interface, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val txtUsername = view.findViewById<TextInputEditText>(R.id.textUsername)
        val txtPassword = view.findViewById<TextInputEditText>(R.id.textPassword)
        val btnAccedi = view.findViewById<Button>(R.id.btnAccedi)

        var pw: String
        var email: String

        btnAccedi.setOnClickListener {
            email = txtUsername.text.toString().trim()
            pw = txtPassword.text.toString().trim()
            if (email.isNotEmpty() && pw.isNotEmpty()) {
                lifecycleScope.launch {
                    if (model.sigIn(email, pw, view) == null) {
                        Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    } else {
                        findNavController().navigate(R.id.action_loginAuthFragment_to_HomePage)
                    }
                }
            }
        }

    }
}