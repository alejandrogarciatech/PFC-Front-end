package com.pfc.android.revisionesapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
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
class MainActivity : AppCompatActivity(), EquipoDetailFragment.OnEditarClickListener {

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

        // SCANNER
        binding.btnScanner.setOnClickListener { initScanner() }
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

    override fun onEditarClick() {
        invalidateOptionsMenu()
        val fragment =
            supportFragmentManager.findFragmentById(R.id.detail_fragmentContainer) as? EquipoDetailFragment
        fragment?.toggleEditMode()
    }

//    private fun crearNuevoEquipo() {
//        val fragment = EquipoDetailFragment.newInstance(nuevoEquipo = true)
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.detail_fragmentContainer, fragment)
//            .addToBackStack(null)
//            .commit()
//    }

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
