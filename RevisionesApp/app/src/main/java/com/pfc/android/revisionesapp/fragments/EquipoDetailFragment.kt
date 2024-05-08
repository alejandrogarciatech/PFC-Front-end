package com.pfc.android.revisionesapp.fragments

import android.content.Context
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

    interface OnEditarClickListener {
        fun onEditarClick()
    }

    private lateinit var binding: FragmentEquipoDetailBinding
    private lateinit var equipoRepository: EquipoRepository
    private var editarClickListener: OnEditarClickListener? = null
    private var modoEdicionActivo: Boolean = false
    val nuevoEquipo: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditarClickListener) {
            editarClickListener = context
        } else {
            throw RuntimeException("$context must implement OnEditarClickListener")
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
        nuevoEquipo(arguments?.getBoolean("nuevoEquipo", false) ?: false)
        if (nuevoEquipo) {
            toggleEditMode()
        } else {
            arguments?.getSerializable("equipo")?.let { it as Equipo }?.let { equipo ->
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
                disableEditMode()
            }
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

    private fun disableEditMode() {
        with(binding) {
            idEquipoEditText.isEnabled = false
            nombreEquipoEditText.isEnabled = false
            tipoProductoEquipoEditText.isEnabled = false
            modeloEquipoEditText.isEnabled = false
            marcaEquipoEditText.isEnabled = false
            nSerieEquipoEditText.isEnabled = false
            pesoEquipoEditText.isEnabled = false
            dimensionesEquipoEditText.isEnabled = false
            ubicacionEquipoEditText.isEnabled = false
        }
    }

    fun toggleEditMode() {
        modoEdicionActivo = !modoEdicionActivo
        with(binding) {
            idEquipoEditText.isEnabled = modoEdicionActivo
            nombreEquipoEditText.isEnabled = modoEdicionActivo
            tipoProductoEquipoEditText.isEnabled = modoEdicionActivo
            modeloEquipoEditText.isEnabled = modoEdicionActivo
            marcaEquipoEditText.isEnabled = modoEdicionActivo
            nSerieEquipoEditText.isEnabled = modoEdicionActivo
            pesoEquipoEditText.isEnabled = modoEdicionActivo
            dimensionesEquipoEditText.isEnabled = modoEdicionActivo
            ubicacionEquipoEditText.isEnabled = modoEdicionActivo
        }
    }

    fun updateEquipo() {
        val id = binding.idEquipoEditText.text.toString()
        val nombre = binding.nombreEquipoEditText.text.toString()
        val tipoProducto = binding.tipoProductoEquipoEditText.text.toString()
        val marca = binding.marcaEquipoEditText.text.toString()
        val modelo = binding.modeloEquipoEditText.text.toString()
        val nSerie = binding.nSerieEquipoEditText.text.toString()
        val peso = binding.pesoEquipoEditText.text.toString().toDouble()
        val dimensiones = binding.dimensionesEquipoEditText.text.toString().toDouble()
        val ubicacion = binding.ubicacionEquipoEditText.text.toString()
        val equipo =
            Equipo(id, nombre, tipoProducto, marca, modelo, nSerie, peso, dimensiones, ubicacion)
        val equipoRepository = EquipoRepository(requireContext())
        equipoRepository.updateEquipo(equipo)
    }

    private fun nuevoEquipo(nuevoEquipo: Boolean) {
        if (nuevoEquipo) {
            toggleEditMode()
        } else {
            disableEditMode()
        }
    }

    fun createEquipo() {
        // Aquí iría la lógica para crear un nuevo equipo
        // Por ejemplo:
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
