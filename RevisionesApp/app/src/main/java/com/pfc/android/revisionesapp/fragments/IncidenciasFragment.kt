package com.pfc.android.revisionesapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pfc.android.revisionesapp.adapters.IncidenciasAdapter
import com.pfc.android.revisionesapp.databinding.FragmentIncidenciasBinding
import com.pfc.android.revisionesapp.interfaces.ApiService
import com.pfc.android.revisionesapp.models.Incidencia

class IncidenciasFragment : Fragment() {

    private lateinit var binding: FragmentIncidenciasBinding
    private lateinit var listaIncidencias: ArrayList<Incidencia>
    private lateinit var incidenciasAdapter: IncidenciasAdapter
    private val apiService = RetrofitClient.retrofit.create(ApiService::class.java)
    private val call = apiService.getIncidencias()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIncidenciasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding
    }
}