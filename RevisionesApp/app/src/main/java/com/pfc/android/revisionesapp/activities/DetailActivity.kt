package com.pfc.android.revisionesapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.pfc.android.revisionesapp.R
import com.pfc.android.revisionesapp.databinding.ActivityDetailBinding
import com.pfc.android.revisionesapp.fragments.EquipoDetailFragment
import com.pfc.android.revisionesapp.models.Equipo

class DetailActivity : AppCompatActivity(), EquipoDetailFragment.OnEditarClickListener {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.detailToolbar)

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_editar -> {
                (supportFragmentManager.findFragmentById(R.id.detail_fragmentContainer) as? EquipoDetailFragment)?.toggleEditMode()
                item.isVisible = false  // Ocultar el elemento "Editar"
                val guardarItem = binding.detailToolbar.menu.findItem(R.id.action_save)
                guardarItem.isVisible = true  // Mostrar el elemento "Guardar"
                true
            }

            R.id.action_save -> {
                (supportFragmentManager.findFragmentById(R.id.detail_fragmentContainer) as? EquipoDetailFragment)?.let { fragment ->
                    fragment.updateEquipo()
                    fragment.toggleEditMode()  // Cambiar al modo de visualización después de guardar los cambios
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