package com.pfc.android.revisionesapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.pfc.android.revisionesapp.repositories.IncidenciaRepository
import com.pfc.android.revisionesapp.databinding.FragmentIncidenciaDetailBinding


class IncidenciaDetailFragment : Fragment() {

    private lateinit var binding: FragmentIncidenciaDetailBinding
    private lateinit var incidenciaRepository: IncidenciaRepository
    private var editarClickListener: EquipoDetailFragment.OnEditarClickListener? = null

    interface OnEditarClickListener {
        fun onEditarClick()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentIncidenciaDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        incidenciaRepository = IncidenciaRepository(requireContext())
        arguments?.getInt("incidenciaId")?.let { incidenciaId ->
            incidenciaRepository.getIncidencia(incidenciaId, {
                binding.idIncidenciaEditText.setText(it.id.toString())
                //binding.fcreacionEditText.setText(it.fechaCreacion)
                binding.descripcionEditText.setText(it.descripcion)
                binding.estadoEditText.setText(it.estado)
            }, {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })
        }
    }
}