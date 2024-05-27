package com.pfc.android.revisionesapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pfc.android.revisionesapp.databinding.FragmentEquipoDetailBinding
import com.pfc.android.revisionesapp.models.Equipo
import com.pfc.android.revisionesapp.repositories.EquipoRepository

@Suppress("DEPRECATION")
class EquipoDetailFragment : Fragment() {

    private lateinit var binding: FragmentEquipoDetailBinding
    private lateinit var equipoRepository: EquipoRepository
    var nuevoEquipo: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nuevoEquipo = it.getBoolean("nuevoEquipo")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEquipoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        equipoRepository = EquipoRepository(requireContext())
        toggleEditMode(nuevoEquipo)
        arguments?.getSerializable("equipo")?.let { it as Equipo }?.let { equipo ->
            fillFields(equipo)
        }
    }

    companion object {
        fun newInstance(nuevoEquipo: Boolean = false): EquipoDetailFragment {
            return EquipoDetailFragment().apply {
                arguments = Bundle().apply {
                    putBoolean("nuevoEquipo", nuevoEquipo)
                }
            }
        }
    }

    fun toggleEditMode(editMode: Boolean) {
        with(binding) {
            idEquipoEditText.isEnabled = editMode
            nombreEquipoEditText.isEnabled = editMode
            tipoProductoEquipoEditText.isEnabled = editMode
            modeloEquipoEditText.isEnabled = editMode
            marcaEquipoEditText.isEnabled = editMode
            nSerieEquipoEditText.isEnabled = editMode
            pesoEquipoEditText.isEnabled = editMode
            dimensionesEquipoEditText.isEnabled = editMode
            ubicacionEquipoEditText.isEnabled = editMode
        }
    }

    private fun fillFields(equipo: Equipo) {
        with(binding) {
            idEquipoEditText.setText(equipo.id)
            nombreEquipoEditText.setText(equipo.nombre)
            tipoProductoEquipoEditText.setText(equipo.tipoProducto)
            modeloEquipoEditText.setText(equipo.marca)
            marcaEquipoEditText.setText(equipo.modelo)
            nSerieEquipoEditText.setText(equipo.nSerie)
            pesoEquipoEditText.setText(equipo.peso.toString())
            dimensionesEquipoEditText.setText(equipo.dimensiones.toString())
            ubicacionEquipoEditText.setText(equipo.ubicacion)
        }
    }

    fun updateEquipo() {
        val equipo = Equipo(
            binding.idEquipoEditText.text.toString(),
            binding.nombreEquipoEditText.text.toString(),
            binding.tipoProductoEquipoEditText.text.toString(),
            binding.marcaEquipoEditText.text.toString(),
            binding.modeloEquipoEditText.text.toString(),
            binding.nSerieEquipoEditText.text.toString(),
            binding.pesoEquipoEditText.text.toString().toDouble(),
            binding.dimensionesEquipoEditText.text.toString().toDouble(),
            binding.ubicacionEquipoEditText.text.toString()
        )
        equipoRepository.updateEquipo(equipo)
    }

    fun createEquipo() {
        val nuevoEquipo = Equipo(
            binding.idEquipoEditText.text.toString(),
            binding.nombreEquipoEditText.text.toString(),
            binding.tipoProductoEquipoEditText.text.toString(),
            binding.marcaEquipoEditText.text.toString(),
            binding.modeloEquipoEditText.text.toString(),
            binding.nSerieEquipoEditText.text.toString(),
            binding.pesoEquipoEditText.text.toString().toDouble(),
            binding.dimensionesEquipoEditText.text.toString().toDouble(),
            binding.ubicacionEquipoEditText.text.toString()
        )
        equipoRepository.createEquipo(nuevoEquipo)
    }
}
