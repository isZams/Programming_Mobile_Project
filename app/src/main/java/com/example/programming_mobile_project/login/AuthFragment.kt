package com.example.programming_mobile_project.login


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.programming_mobile_project.MainActivity
import com.example.programming_mobile_project.R
import com.example.programming_mobile_project.models.Utente
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

/**
     fragment utilizzato per la pagina Registrati.
 */

class AuthFragment : Fragment() {

    private lateinit var utente: Utente

    /**
    * onCreateView utilizzato per l'inflate layout di google_login (layout pagina registrati)
    * @return inflate layout google_login
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.google_login, container, false)
    }

    /**
    * onViewCreated eseguito dopo la creazione del Fragment. Utilizzato per inizializzare gli elementi del layout a cui fa riferimento
    * bottone registrati che controlla se gli elementi InputEditText non sono vuoti allora lancia la funzione signUp del viewModel associato per la registrazione dell'utente e
    * se questa è andata a buon fine prende le credenziali dell'utente e le registra nel Cloud Storage di Firebase
    * lifecycleScope utilizzato per lanciare la coroutine
    * se l'utente è già registrato cliccando su Login verrà trasportato alla pagina di login dove potrà autenticarsi
    */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = AuthViewModel()
        val txtUsername = view.findViewById<EditText>(R.id.textUsername)
        val txtPassword = view.findViewById<TextInputEditText>(R.id.textPassword)
        val txtNome = view.findViewById<TextInputEditText>(R.id.textNome)
        val txtCognome = view.findViewById<TextInputEditText>(R.id.textCognome)
        val btnRegistrati = view.findViewById<Button>(R.id.btnRegistrati)

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
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                }
            }
        }
    }

}