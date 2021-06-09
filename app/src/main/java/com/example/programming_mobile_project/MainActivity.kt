package com.example.programming_mobile_project

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.programming_mobile_project.Home_Page.HomePage
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      //  val nav_View = findViewById<BottomNavigationView>(R.id.nav_view)
        val navHostfragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        setSupportActionBar(findViewById<Toolbar>(R.id.main_toolbar))

   //     val firstFragment = HomePage()

     //   setCurrentFragment(firstFragment)

     /*   nav_View.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.HomePage -> setCurrentFragment(firstFragment)
            }
            true

        }*/
    }


    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.HomePage, fragment)
            commit()
        }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
/*
    fun sendHtmlEmail(view: View?) {
        val mailId = "Beach.View@gmail.com"
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", mailId, null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Salve")
        startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }
*/}