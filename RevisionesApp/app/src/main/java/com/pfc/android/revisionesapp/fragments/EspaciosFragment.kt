package com.pfc.android.revisionesapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.pfc.android.revisionesapp.activities.DetailActivity
import com.pfc.android.revisionesapp.activities.MainActivity
import com.pfc.android.revisionesapp.adapters.EspaciosAdapter
import com.pfc.android.revisionesapp.databinding.FragmentEspaciosBinding
import com.pfc.android.revisionesapp.interfaces.ApiService
import com.pfc.android.revisionesapp.models.Espacio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EspaciosFragment : Fragment() {

    private lateinit var binding: FragmentEspaciosBinding
    private lateinit var listaEspacios: ArrayList<Espacio>
    private lateinit var espaciosAdapter: EspaciosAdapter
    private val apiService = RetrofitClient.instance.create(ApiService::class.java)
    private val call = apiService.getEspacios()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEspaciosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstaceState: Bundle?){
        super.onViewCreated(view, savedInstaceState)

        val mainActivity = activity as MainActivity
        mainActivity.supportActionBar?.title = "Espacios"

        espaciosAdapter = EspaciosAdapter(ArrayList(), requireContext())
        listaEspacios = ArrayList()
        espaciosAdapter = EspaciosAdapter(listaEspacios, requireContext())

        binding.espaciosRecyclerView.adapter = espaciosAdapter
        binding.espaciosRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.espaciosRecyclerView.adapter = espaciosAdapter
        verEspacios()
    }

    private fun verEspacios() {
        call.enqueue(object : Callback<List<Espacio>> {
            override fun onResponse(
                call: Call<List<Espacio>>,
                response: Response<List<Espacio>>
            ) {
                if (response.isSuccessful) {
                    val espacios = response.body()
                    if (espacios != null) {
                        Log.d("Espacios", espacios.toString())
                        listaEspacios = espacios as ArrayList<Espacio>
                        espaciosAdapter.updateList(listaEspacios)

                        espaciosAdapter.setOnItemClickListener(object :
                            EspaciosAdapter.OnItemClickListener {
                            override fun onItemClick(espacio: Espacio) {
                                val intent = Intent(requireActivity(), DetailActivity::class.java)
                                intent.putExtra("espacio", espacio)
                                startActivity(intent)
                            }
                        })
                    } else {
                        Log.e("Espacios", "la lista de espacios es nula")
                    }
                }
            }

            override fun onFailure(call: Call<List<Espacio>>, t: Throwable) {
                Log.e("Incidencias", "Error al realizar la solicitud: ${t.message}")
            }
        })
    }
}