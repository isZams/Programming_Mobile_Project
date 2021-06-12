package com.example.programming_mobile_project

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.programming_mobile_project.Home_Page.HomePage
import com.example.programming_mobile_project.login.AuthViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        setSupportActionBar(findViewById<Toolbar>(R.id.main_toolbar))
        bottomNav.setOnNavigationItemSelectedListener OnNavigationItemSelectedListener@{ item ->
            when (item.itemId) {
                R.id.item1 -> {
                    val navController = findNavController(R.id.nav_host_fragment)
                    navController.navigate(R.id.HomePage)
                    true
                }
                R.id.item2 -> {
                    sendHtmlEmail()
                    true
                }
                else -> false
            }
        }

        val drawerNav = findViewById<NavigationView>(R.id.nav_view)
        drawer = findViewById(R.id.drawer_layout)
        drawerNav.setNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer.closeDrawer(GravityCompat.START)
        val navController = findNavController(R.id.nav_host_fragment)
        when (item.itemId){
            R.id.nav_prenotation -> {
                navController.navigate(R.id.elencoPrenotazioni)
            }
            R.id.nav_update ->{
                navController.navigate(R.id.modificaDati)
            }
            R.id.logout -> {
                val modelAuth = AuthViewModel(this)
                modelAuth.logOut()
                navController.navigate(R.id.login)
            }
        }

        return true
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
    fun sendHtmlEmail() {
        val mailId = "Beach.View@gmail.com"
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", mailId, null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Salve")
        startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }
}

