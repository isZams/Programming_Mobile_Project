package com.example.programming_mobile_project

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.programming_mobile_project.database.UtenteDB
import com.example.programming_mobile_project.login.AuthViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        drawer = findViewById(R.id.drawer_layout)

        val utenteDB = UtenteDB()
        lifecycleScope.launch {
            val utente = utenteDB.getUtente()
            Log.i("ditto", utente.toString())
            if (utente != null)
                drawer.findViewById<TextView>(R.id.drawerTitle).text =
                    "${utente.nome} ${utente.cognome}"
        }

        /**
         * Inietta le componenti per la toolbar e il drawer in modo da mostrare il tasto indietro nei fragment successivi alla HomePage
         */
        setSupportActionBar(toolbar)

        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.HomePage),
            drawerLayout = drawer
        )
        findViewById<Toolbar>(R.id.main_toolbar)
            .setupWithNavController(navController, appBarConfiguration)
        //setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNav.setOnNavigationItemSelectedListener OnNavigationItemSelectedListener@{ item ->
            when (item.itemId) {
                R.id.item1 -> {
                    navController.popBackStack(R.id.HomePage, false)
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
        drawerNav.setNavigationItemSelectedListener(this)
    }

    /**
     * Nel caso si prema logout allora viene terminata la MainActivity e viene avviata
     * con un intent la LoginActivity
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer.closeDrawer(GravityCompat.START)
        val navController = findNavController(R.id.nav_host_fragment)
        when (item.itemId) {
            R.id.nav_prenotation -> {
                navController.navigate(R.id.action_HomePage_to_elencoPrenotazioni)
            }
            R.id.nav_update -> {
                navController.navigate(R.id.action_HomePage_to_modificaDati)
            }
            R.id.logout -> {
                val modelAuth = AuthViewModel()
                modelAuth.logOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                this.finish()
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

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}