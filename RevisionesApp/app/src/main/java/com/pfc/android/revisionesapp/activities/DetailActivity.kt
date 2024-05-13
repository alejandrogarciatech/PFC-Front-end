package com.pfc.android.revisionesapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.pfc.android.revisionesapp.R
import com.pfc.android.revisionesapp.databinding.ActivityDetailBinding
import com.pfc.android.revisionesapp.fragments.EquipoDetailFragment
import com.pfc.android.revisionesapp.fragments.IncidenciaDetailFragment
import com.pfc.android.revisionesapp.models.Equipo

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity(), EquipoDetailFragment.OnEditarClickListener, IncidenciaDetailFragment.OnEditarClickListener {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var incidenciaDetailFragment: IncidenciaDetailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar la toolbar
        setSupportActionBar(binding.detailToolbar)

        // Obtener el id de la incidencia
        val incidenciaId = intent.getIntExtra("incidenciaId", -1)
        if (incidenciaId != -1) {
            incidenciaDetailFragment = IncidenciaDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt("incidenciaId", incidenciaId)
                }
            }
            supportFragmentManager.beginTransaction()
                .add(R.id.detail_fragmentContainer, incidenciaDetailFragment)
                .commit()
        }

        // Obtener el id del equipo
        if (intent.extras?.getBoolean("nuevoEquipo", false) == true) {
            val fragment = EquipoDetailFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.detail_fragmentContainer, fragment)
                .commit()
        } else {
            savedInstanceState ?: run {
                intent.getSerializableExtra("equipo")?.let { it as Equipo }?.let { equipo ->
                    EquipoDetailFragment().apply {
                        arguments = Bundle().apply {
                            putSerializable("equipo", equipo)
                        }
                    }.also { fragment ->
                        supportFragmentManager.beginTransaction()
                            .add(R.id.detail_fragmentContainer, fragment)
                            .commit()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_editar -> {
                // Aquí estás pasando un indicador de que se está creando un nuevo equipo
                val fragment = EquipoDetailFragment.newInstance(nuevoEquipo = true)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.detail_fragmentContainer, fragment)
                    .commit()
                item.isVisible = false  // Ocultar el elemento "Editar"
                val guardarItem = binding.detailToolbar.menu.findItem(R.id.action_save)
                guardarItem.isVisible = true  // Mostrar el elemento "Guardar"
                true
            }

            R.id.action_save -> {
                val equipoDetailFragment =
                    supportFragmentManager.findFragmentById(R.id.detail_fragmentContainer) as? EquipoDetailFragment
                equipoDetailFragment?.let { fragment ->
                    if (fragment.nuevoEquipo) {
                        fragment.createEquipo()
                    } else {
                        fragment.updateEquipo()
                        fragment.toggleEditMode()
                    }
                }
                item.isVisible = false  // Ocultar el elemento "Guardar"
                val editarItem = binding.detailToolbar.menu.findItem(R.id.action_editar)
                editarItem.isVisible = true  // Mostrar el elemento "Editar"
                true
            }

            R.id.navigation_back -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onEditarClick() {
        invalidateOptionsMenu()
        (supportFragmentManager.findFragmentById(R.id.detail_fragmentContainer) as? EquipoDetailFragment)?.toggleEditMode()
    }

}
