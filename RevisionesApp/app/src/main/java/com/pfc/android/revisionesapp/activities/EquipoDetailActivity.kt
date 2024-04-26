package com.pfc.android.revisionesapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pfc.android.revisionesapp.databinding.ActivityDetalleEquipoBinding
import com.pfc.android.revisionesapp.models.Equipo
@Suppress("DEPRECATION")
class EquipoDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleEquipoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleEquipoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val equipo = intent.getSerializableExtra("equipo") as Equipo
        binding.idEquipoTextView.text = equipo.id

        binding.nombreEquipoTextView.text = equipo.nombre
        binding.modeloEquipoTextView.text = equipo.marca
        binding.marcaEquipoTextView.text = equipo.modelo
    }
}
