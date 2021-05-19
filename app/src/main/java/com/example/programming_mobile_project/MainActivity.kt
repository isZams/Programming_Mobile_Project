package com.example.programming_mobile_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.programming_mobile_project.login.AuthFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostfragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val nav_controller = navHostfragment.navController
    }
}