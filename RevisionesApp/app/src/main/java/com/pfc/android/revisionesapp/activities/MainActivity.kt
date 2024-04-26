package com.pfc.android.revisionesapp.activities

import android.content.Intent
import android.os.Bundle
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
                    // Handle navigation to dashboard
                    true
                }
                R.id.navigation_incidencias -> {
                    // Handle navigation to notifications
                    true
                }
                else -> false
            }
        }
    }

    fun irAEquiposActivity(view: View) {
        val intent = Intent(this, EquiposActivity::class.java)
        startActivity(intent)
    }
}