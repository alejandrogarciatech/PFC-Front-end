package com.pfc.android.revisionesapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.zxing.integration.android.IntentIntegrator
import com.pfc.android.revisionesapp.R
import com.pfc.android.revisionesapp.databinding.ActivityMainBinding
import com.pfc.android.revisionesapp.fragments.AlbaranesFragment
import com.pfc.android.revisionesapp.fragments.EquipoDetailFragment
import com.pfc.android.revisionesapp.fragments.EquiposFragment
import com.pfc.android.revisionesapp.fragments.EspaciosFragment
import com.pfc.android.revisionesapp.fragments.IncidenciasFragment
import com.pfc.android.revisionesapp.fragments.InicioFragment
import com.pfc.android.revisionesapp.interfaces.ApiService
import com.pfc.android.revisionesapp.models.Equipo
import com.pfc.android.revisionesapp.repositories.EquipoRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Barras de herramientas
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setSupportActionBar(binding.toolbar)

        // Barra de navegación inferior
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.navigation_home -> InicioFragment()
                R.id.navigation_equipos -> EquiposFragment()
                R.id.navigation_incidencias -> IncidenciasFragment()
                R.id.navigation_albaranes -> AlbaranesFragment()
                R.id.navigation_espacios -> EspaciosFragment()
                else -> null
            }

            if (fragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit()
            }

            true
        }

        if (savedInstanceState == null) {
            val inicioFragment = InicioFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, inicioFragment)
                .commit()
        }

        // Set the scanner button
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
            if (result.contents == null) {
                Toast.makeText(this@MainActivity, "Cancelado", Toast.LENGTH_SHORT).show()
            } else {
                val equipoId = result.contents
                getEquipo(equipoId)
                Toast.makeText(
                    this@MainActivity,
                    "El equipo escaneado es $equipoId",
                    Toast.LENGTH_SHORT
                ).show()
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
        val fragment = EquipoDetailFragment()
        val bundle = Bundle()
        bundle.putSerializable("equipo", equipo)
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (currentFragment !is InicioFragment) {
            val inicioFragment = InicioFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, inicioFragment)
                .commit()
            binding.bottomNavigation.selectedItemId = R.id.navigation_home
        } else {
            super.onBackPressed()
        }
    }
}
