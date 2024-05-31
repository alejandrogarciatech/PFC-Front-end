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

    fun toggleEditMode(editMode: Boolean): Boolean {
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
        return editMode
    }

    private fun fillFields(equipo: Equipo) {
        with(binding) {
            idEquipoEditText.editText?.setText(equipo.id)
            nombreEquipoEditText.editText?.setText(equipo.nombre)
            tipoProductoEquipoEditText.editText?.setText(equipo.tipoProducto)
            modeloEquipoEditText.editText?.setText(equipo.marca)
            marcaEquipoEditText.editText?.setText(equipo.modelo)
            nSerieEquipoEditText.editText?.setText(equipo.nSerie)
            pesoEquipoEditText.editText?.setText(equipo.peso.toString())
            dimensionesEquipoEditText.editText?.setText(equipo.dimensiones.toString())
            binding.ubicacionEquipoEditText.editText?.setText(equipo.ubicacion)
        }
    }

    fun updateEquipo() {
        val equipo = Equipo(
            binding.idEquipoEditText.editText?.text.toString(),
            binding.nombreEquipoEditText.editText?.text.toString(),
            binding.tipoProductoEquipoEditText.editText?.text.toString(),
            binding.marcaEquipoEditText.editText?.text.toString(),
            binding.modeloEquipoEditText.editText?.text.toString(),
            binding.nSerieEquipoEditText.editText?.text.toString(),
            binding.pesoEquipoEditText.editText?.text.toString().toDouble(),
            binding.dimensionesEquipoEditText.editText?.text.toString().toDouble(),
            binding.ubicacionEquipoEditText.editText?.text.toString()
        )
        equipoRepository.updateEquipo(equipo)
    }

    fun createEquipo() {
        val nuevoEquipo = Equipo(
            binding.idEquipoEditText.editText?.text.toString(),
            binding.nombreEquipoEditText.editText?.text.toString(),
            binding.tipoProductoEquipoEditText.editText?.text.toString(),
            binding.marcaEquipoEditText.editText?.text.toString(),
            binding.modeloEquipoEditText.editText?.text.toString(),
            binding.nSerieEquipoEditText.editText?.text.toString(),
            binding.pesoEquipoEditText.editText?.text.toString().toDouble(),
            binding.dimensionesEquipoEditText.editText?.text.toString().toDouble(),
            binding.ubicacionEquipoEditText.editText?.text.toString()
        )
        equipoRepository.createEquipo(nuevoEquipo)
    }

    fun deleteEquipo(){
        val equipo = Equipo(
            binding.idEquipoEditText.editText?.text.toString(),
            binding.nombreEquipoEditText.editText?.text.toString(),
            binding.tipoProductoEquipoEditText.editText?.text.toString(),
            binding.marcaEquipoEditText.editText?.text.toString(),
            binding.modeloEquipoEditText.editText?.text.toString(),
            binding.nSerieEquipoEditText.editText?.text.toString(),
            binding.pesoEquipoEditText.editText?.text.toString().toDouble(),
            binding.dimensionesEquipoEditText.editText?.text.toString().toDouble(),
            binding.ubicacionEquipoEditText.editText?.text.toString()
        )
        equipoRepository.deleteEquipo(equipo.id)
    }
}
