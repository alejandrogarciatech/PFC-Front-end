package com.pfc.android.revisionesapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.zxing.integration.android.IntentIntegrator
import com.pfc.android.revisionesapp.R
import com.pfc.android.revisionesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Barras de herramientas
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setSupportActionBar(binding.include.toolbar)

        // Barra de navegación inferior
        binding.include.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Handle navigation to home
                    true
                }

                R.id.navigation_equipos -> {
                    val intent = Intent(this, EquiposActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_incidencias -> {
                    val intent = Intent(this, IncidenciasActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_albaranes -> {
                    val intent = Intent(this, AlbaranesActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.navigation_espacios -> {
                    val intent = Intent(this, EspaciosActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    fun irAEquiposActivity(view: View) {
        val intent = Intent(this, EquiposActivity::class.java)
        startActivity(intent)
    }
    fun irAIncidenciasActivity(view: View) {
        val intent = Intent(this, IncidenciasActivity::class.java)
        startActivity(intent)
    }
    fun irAAlbaranesActivity(view: View) {
        val intent = Intent(this, AlbaranesActivity::class.java)
        startActivity(intent)
    }
    fun irAEspaciosActivity(view: View) {
        val intent = Intent(this, EspaciosActivity::class.java)
        startActivity(intent)
    }
    fun scanner(view: View) {
        val scannerFragment = ScannerFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, scannerFragment).commit()
    }
}