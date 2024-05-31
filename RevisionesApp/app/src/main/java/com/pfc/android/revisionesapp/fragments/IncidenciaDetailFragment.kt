package com.pfc.android.revisionesapp.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.pfc.android.revisionesapp.databinding.FragmentIncidenciaDetailBinding
import com.pfc.android.revisionesapp.models.Incidencia
import com.pfc.android.revisionesapp.repositories.IncidenciaRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Suppress("DEPRECATION")
class IncidenciaDetailFragment : Fragment() {

    private lateinit var binding: FragmentIncidenciaDetailBinding
    private lateinit var incidenciaRepository: IncidenciaRepository
    var nuevaIncidencia: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nuevaIncidencia = it.getBoolean("nuevaIncidencia")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIncidenciaDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        incidenciaRepository = IncidenciaRepository(requireContext())
        toggleEditMode(nuevaIncidencia)
        arguments?.getSerializable("incidencia")?.let { it as Incidencia }?.let { incidencia ->
            fillFields(incidencia)
        }
    }

    companion object {
        fun newInstance(nuevaIncidencia: Boolean = false): IncidenciaDetailFragment {
            return IncidenciaDetailFragment().apply {
                arguments = Bundle().apply {
                    putBoolean("nuevaIncidencia", nuevaIncidencia)
                }
            }
        }
    }

    fun toggleEditMode(editMode: Boolean) {
        with(binding) {
            idIncidenciaEditText.isEnabled = editMode
            descripcionEditText.isEnabled = editMode
            estadoEditText.isEnabled = editMode
            prioridadEditText.isEnabled = editMode
            albaranEditText.isEnabled = editMode
            usuarioEditText.isEnabled = editMode
            equipoEditText.isEnabled = editMode
        }
    }

    private fun fillFields(incidencia: Incidencia){
        with(binding){
            idIncidenciaEditText.setText(incidencia.id.toString())
            descripcionEditText.setText(incidencia.descripcion)
            estadoEditText.setText(incidencia.estado)
            prioridadEditText.setText(incidencia.prioridad)
            albaranEditText.setText(incidencia.idAlbaran)
            usuarioEditText.setText(incidencia.idUsuario)
            fcreacionEditText.setText(incidencia.createAt)
            factualizacionEditText.setText(incidencia.updateAt)
            //equipoEditText.setText(incidencia.equipoId)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateIncidencia() {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formatted = currentDateTime.format(formatter)

        val incidencia = Incidencia(
            binding.idIncidenciaEditText.text.toString().toInt(),
            binding.descripcionEditText.text.toString(),
            binding.estadoEditText.text.toString(),
            binding.prioridadEditText.text.toString(),
            binding.albaranEditText.text.toString(),
            binding.usuarioEditText.text.toString(),
            binding.fcreacionEditText.text.toString(),
            formatted,
            binding.equipoEditText.text.toString()
        )
        incidenciaRepository.updateIncidencia(incidencia)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createIncidencia() {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formatted = currentDateTime.format(formatter)

        val incidencia = Incidencia(
            binding.idIncidenciaEditText.text.toString().toInt(),
            binding.descripcionEditText.text.toString(),
            binding.estadoEditText.text.toString(),
            binding.prioridadEditText.text.toString(),
            binding.albaranEditText.text.toString(),
            binding.usuarioEditText.text.toString(),
            formatted,
            binding.factualizacionEditText.text.toString(),
            binding.equipoEditText.text.toString()
        )
        incidenciaRepository.createIncidencia(incidencia)
    }

    fun deleteIncidencia(){
        val incidencia = Incidencia(
            binding.idIncidenciaEditText.text.toString().toInt(),
            binding.descripcionEditText.text.toString(),
            binding.estadoEditText.text.toString(),
            binding.prioridadEditText.text.toString(),
            binding.albaranEditText.text.toString(),
            binding.usuarioEditText.text.toString(),
            binding.fcreacionEditText.text.toString(),
            binding.factualizacionEditText.text.toString(),
            binding.equipoEditText.text.toString()
        )
        incidenciaRepository.deleteIncidencia(incidencia.id)
    }
}

