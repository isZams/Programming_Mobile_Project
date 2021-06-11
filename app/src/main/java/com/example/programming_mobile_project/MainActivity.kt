package com.example.programming_mobile_project

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import com.example.programming_mobile_project.Home_Page.HomePage
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNav = findViewById<BottomNavigationView>(R.id.nav_view)

        bottomNav.setOnNavigationItemSelectedListener OnNavigationItemSelectedListener@{ item ->
            when (item.itemId) {
                R.id.item1 -> {
                    val fragment: Fragment = HomePage()
                    supportFragmentManager.beginTransaction()
                       .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                    true
                }
                R.id.item2 -> {
                    sendHtmlEmail()
                    true
                }
                else -> false
            }
        }


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        setSupportActionBar(findViewById<Toolbar>(R.id.main_toolbar))
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
