package com.pfc.android.revisionesapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.pfc.android.revisionesapp.R
import com.pfc.android.revisionesapp.databinding.ActivityDetailBinding
import com.pfc.android.revisionesapp.fragments.EquipoDetailFragment
import com.pfc.android.revisionesapp.fragments.IncidenciaDetailFragment
import com.pfc.android.revisionesapp.models.Equipo
import com.pfc.android.revisionesapp.models.Incidencia

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar la toolbar
        setSupportActionBar(binding.detailToolbar)

        // Configurar botón borrado
        binding.btnDelete.isVisible = false
        binding.btnDelete.setOnClickListener {
            val equipoDetailFragment =
                supportFragmentManager.findFragmentById(R.id.detail_fragmentContainer) as? EquipoDetailFragment
            equipoDetailFragment?.let { fragment ->
                AlertDialog.Builder(this)
                    .setTitle("Confirmación de borrado")
                    .setMessage("¿Estás seguro de que quieres borrar este equipo?")
                    .setPositiveButton("Aceptar") { _, _ ->
                        fragment.deleteEquipo()
                        onBackPressed() // Volver al fragmento anterior
                    }
                    .setNegativeButton("Cancelar", null) // No hacer nada al cancelar
                    .show()
            }
            val incidenciaDetailFragment =
                supportFragmentManager.findFragmentById(R.id.detail_fragmentContainer) as? IncidenciaDetailFragment
            incidenciaDetailFragment?.let { fragment ->
                AlertDialog.Builder(this)
                    .setTitle("Confirmación de borrado")
                    .setMessage("¿Estás seguro de que quieres borrar esta incidencia?")
                    .setPositiveButton("Aceptar") { _, _ ->
                        fragment.deleteIncidencia()
                        onBackPressed() // Volver al fragmento anterior
                    }
                    .setNegativeButton("Cancelar", null) // No hacer nada al cancelar
                    .show()
            }
        }

        if (intent.extras?.getBoolean("nuevoEquipo", false) == true) {
            val fragment = EquipoDetailFragment.newInstance(nuevoEquipo = true)
            supportFragmentManager.beginTransaction()
                .add(R.id.detail_fragmentContainer, fragment)
                .commit()
            invalidateOptionsMenu()
        } else {
            savedInstanceState ?: run {
                intent.getSerializableExtra("equipo")?.let { it as Equipo }?.let { equipo ->
                    EquipoDetailFragment.newInstance(nuevoEquipo = false).apply {
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

        if (intent.extras?.getBoolean("nuevaIncidencia", false) == true) {
            val fragment = IncidenciaDetailFragment.newInstance(nuevaIncidencia = true)
            supportFragmentManager.beginTransaction()
                .add(R.id.detail_fragmentContainer, fragment)
                .commit()
            invalidateOptionsMenu()
        } else {
            savedInstanceState ?: run {
                intent.getSerializableExtra("incidencia")?.let { it as Incidencia }
                    ?.let { incidencia ->
                        IncidenciaDetailFragment.newInstance(nuevaIncidencia = false).apply {
                            arguments = Bundle().apply {
                                putSerializable("incidencia", incidencia)
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

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val equipoDetailFragment =
            supportFragmentManager.findFragmentById(R.id.detail_fragmentContainer) as? EquipoDetailFragment
        equipoDetailFragment?.let { fragment ->
            menu.findItem(R.id.action_save).isVisible = fragment.nuevoEquipo
            menu.findItem(R.id.action_editar).isVisible = !fragment.nuevoEquipo
        }
        val incidenciaDetailFragment =
            supportFragmentManager.findFragmentById(R.id.detail_fragmentContainer) as? IncidenciaDetailFragment
        incidenciaDetailFragment?.let { fragment ->
            menu.findItem(R.id.action_save).isVisible = fragment.nuevaIncidencia
            menu.findItem(R.id.action_editar).isVisible = !fragment.nuevaIncidencia
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_editar -> {
                (supportFragmentManager.findFragmentById(R.id.detail_fragmentContainer) as? IncidenciaDetailFragment)?.toggleEditMode(
                    true
                )
                (supportFragmentManager.findFragmentById(R.id.detail_fragmentContainer) as? EquipoDetailFragment)?.toggleEditMode(
                    true
                )
                item.isVisible = false  // Ocultar el elemento "Editar"
                val guardarItem = binding.detailToolbar.menu.findItem(R.id.action_save)
                guardarItem.isVisible = true  // Mostrar el elemento "Guardar"
                binding.btnDelete.isVisible = true  // Mostrar el botón de borrado
                true
            }

            R.id.action_save -> {
                val incidenciaDetailFragment =
                    supportFragmentManager.findFragmentById(R.id.detail_fragmentContainer) as? IncidenciaDetailFragment
                incidenciaDetailFragment?.let { fragment ->
                    if (fragment.nuevaIncidencia) {
                        fragment.createIncidencia()
                    } else {
                        fragment.updateIncidencia()
                    }
                    fragment.toggleEditMode(false)  // Desactivar la edición
                }
                val equipoDetailFragment =
                    supportFragmentManager.findFragmentById(R.id.detail_fragmentContainer) as? EquipoDetailFragment
                equipoDetailFragment?.let { fragment ->
                    if (fragment.nuevoEquipo) {
                        fragment.createEquipo()
                    } else {
                        fragment.updateEquipo()
                    }
                    fragment.toggleEditMode(false)  // Desactivar la edición
                }
                item.isVisible = false  // Ocultar el elemento "Guardar"
                val editarItem = binding.detailToolbar.menu.findItem(R.id.action_editar)
                editarItem.isVisible = true  // Mostrar el elemento "Editar"
                binding.btnDelete.isVisible = false // Ocultar el botón de borrado
                true
            }

            R.id.navigation_back -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
