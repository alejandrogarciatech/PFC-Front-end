package com.pfc.android.revisionesapp.fragments

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pfc.android.revisionesapp.databinding.FragmentIncidenciaDetailBinding
import com.pfc.android.revisionesapp.models.Incidencia
import com.pfc.android.revisionesapp.repositories.IncidenciaRepository
import java.util.Locale


@Suppress("DEPRECATION")
class IncidenciaDetailFragment : Fragment() {

    private lateinit var binding: FragmentIncidenciaDetailBinding
    private lateinit var incidenciaRepository: IncidenciaRepository
//    private var editarClickListener: OnEditarClickListener? = null
//    private var modoEdicionActivo: Boolean = false
//    val nuevaIncidencia: Boolean = false

    interface OnEditarClickListener {
        fun onEditarClick()
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
        arguments?.getSerializable("incidencia")?.let { it as Incidencia }?.let { incidencia ->
            with(binding) {
                idIncidenciaEditText.setText(incidencia.id.toString())
                descripcionEditText.setText(incidencia.descripcion)
                estadoEditText.setText(incidencia.estado)
                prioridadEditText.setText(incidencia.prioridad)
                albaranEditText.setText(incidencia.idAlbaran.toString())
                usuarioEditText.setText(incidencia.idUsuario.toString())

                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                //fcreacionEditText.setText(dateFormat.format(incidencia.fechaCreacion))
                //factualizacionEditText.setText(dateFormat.format(incidencia.fechaActualizacion))

                equipoEditText.setText(incidencia.equipoId)
            }
        }
    }

    fun toggleEditMode() {
        TODO("Not yet implemented")
    }
}
