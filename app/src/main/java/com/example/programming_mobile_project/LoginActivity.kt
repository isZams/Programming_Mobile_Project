package com.example.programming_mobile_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController

class LoginActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.login_nav_host) as NavHostFragment
        navController = navHostFragment.findNavController()
    }
}