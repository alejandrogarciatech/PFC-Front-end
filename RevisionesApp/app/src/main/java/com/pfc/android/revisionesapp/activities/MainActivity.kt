package com.pfc.android.revisionesapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.zxing.integration.android.IntentIntegrator
import com.pfc.android.revisionesapp.R
import com.pfc.android.revisionesapp.databinding.ActivityMainBinding
import com.pfc.android.revisionesapp.fragments.EquipoDetailFragment
import com.pfc.android.revisionesapp.models.Equipo
import com.pfc.android.revisionesapp.repositories.EquipoRepository

@Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        // BOTTOM NAVIGATION
        binding.bottomNavigation.setupWithNavController(navController)

        // TOOLBAR
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setupWithNavController(navController)

        // SCANNER
        binding.btnScanner.setOnClickListener { initScanner() }

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.inicioFragment -> {
                    if (navController.currentDestination?.id != R.id.inicioFragment) {
                        navController.navigate(R.id.inicioFragment)
                    }
                    true
                }
                R.id.equiposFragment -> {
                    if (navController.currentDestination?.id != R.id.equiposFragment) {
                        navController.navigate(R.id.equiposFragment)
                    }
                    true
                }
                R.id.incidenciasFragment -> {
                    if (navController.currentDestination?.id != R.id.incidenciasFragment) {
                        navController.navigate(R.id.incidenciasFragment)
                    }
                    true
                }
                R.id.albaranesFragment -> {
                    if (navController.currentDestination?.id != R.id.albaranesFragment) {
                        navController.navigate(R.id.albaranesFragment)
                    }
                    true
                }
                R.id.espaciosFragment -> {
                    if (navController.currentDestination?.id != R.id.espaciosFragment) {
                        navController.navigate(R.id.espaciosFragment)
                    }
                    true
                }
                // Repite para los demás ítems de la barra de navegación
                else -> false
            }
        }
    }

    private fun initScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Escanea el código QR del equipo")
        integrator.setTorchEnabled(true)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                val equipoId = result.contents
                getEquipo(equipoId)
            } else {
                Toast.makeText(this@MainActivity, "Cancelado", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun getEquipo(equipoId: String) {
        val equipoRepository = EquipoRepository(this)
        equipoRepository.getEquipo(equipoId,
            onSuccess = { equipo ->
                abrirDetalleEquipo(equipo)
            },
            onError = { error ->
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun abrirDetalleEquipo(equipo: Equipo) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("equipo", equipo)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.perfilUsuarioActivity -> {
                val intent = Intent(this, PerfilUsuarioActivity::class.java)
                // Aquí puedes agregar cualquier dato extra que quieras pasar a PerfilUsuarioActivity
                // por ejemplo:
                intent.putExtra("USERNAME", "nombreDeUsuario")
                intent.putExtra("PASSWORD", "contraseña")
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Sobreescribir el método onBackPressed para controlar la navegación
    override fun onBackPressed() {

        val navController = findNavController(R.id.navHostFragment)
        val currentDestination = navController.currentDestination

        // Verificar si hay un destino en la pila hacia el cual deberíamos retroceder
        if (currentDestination?.id != R.id.inicioFragment) {
            // Si no estamos en el fragmento de inicio, retrocedemos al fragmento anterior
            navController.navigateUp()
        } else {
            // Si estamos en el fragmento de inicio, llamamos al comportamiento predeterminado de onBackPressed
            super.onBackPressed()
        }
    }
}
