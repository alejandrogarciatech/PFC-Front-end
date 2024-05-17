package com.pfc.android.revisionesapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.pfc.android.revisionesapp.activities.DetailActivity
import com.pfc.android.revisionesapp.adapters.IncidenciasAdapter
import com.pfc.android.revisionesapp.databinding.FragmentIncidenciasBinding
import com.pfc.android.revisionesapp.interfaces.ApiService
import com.pfc.android.revisionesapp.models.Incidencia
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IncidenciasFragment : Fragment() {

    private lateinit var binding: FragmentIncidenciasBinding
    private lateinit var listaIncidencias: ArrayList<Incidencia>
    private lateinit var incidenciasAdapter: IncidenciasAdapter
    private val apiService = RetrofitClient.instance.create(ApiService::class.java)
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

        incidenciasAdapter = IncidenciasAdapter(ArrayList(), requireContext())
        listaIncidencias = ArrayList()
        incidenciasAdapter = IncidenciasAdapter(listaIncidencias, requireContext())

        binding.incidenciasRecyclerView.adapter = incidenciasAdapter
        binding.incidenciasRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        call.enqueue(object : Callback<List<Incidencia>> {
            override fun onResponse(call: Call<List<Incidencia>>, response: Response<List<Incidencia>>) {
                if (response.isSuccessful) {
                    val incidencias = response.body()
                    if (incidencias != null) {
                        Log.d("Incidencias", incidencias.toString())
                        listaIncidencias = incidencias as ArrayList<Incidencia>
                        incidenciasAdapter.updateList(listaIncidencias)

                        incidenciasAdapter.setOnItemClickListener(object :
                            IncidenciasAdapter.OnItemClickListener {
                            override fun onItemClick(incidencia: Incidencia) {
                                val intent = Intent(requireActivity(), DetailActivity::class.java)
                                intent.putExtra("incidencia", incidencia)
                                startActivity(intent)
                            }
                        })
                    } else {
                        Log.e("Incidencias", "la lista de incidencias es nula")
                    }
                }
            }
            override fun onFailure(call: Call<List<Incidencia>>, t: Throwable) {
                Log.e("Incidencias", "Error al realizar la solicitud: ${t.message}")
            }
        })
    }
}