package com.pfc.android.revisionesapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pfc.android.revisionesapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Aquí se establece activity_main.xml como el diseño de contenido de MainActivity

        // Barras de herramientas
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Barra de navegación inferior
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
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
}