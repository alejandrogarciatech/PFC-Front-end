package com.pfc.android.revisionesapp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.pfc.android.revisionesapp.activities.DetailActivity
import com.pfc.android.revisionesapp.adapters.EquipoAdapter
import com.pfc.android.revisionesapp.databinding.FragmentEquiposBinding
import com.pfc.android.revisionesapp.interfaces.ApiService
import com.pfc.android.revisionesapp.models.Equipo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EquiposFragment : Fragment() {

    private lateinit var binding: FragmentEquiposBinding
    private lateinit var listaEquipo: ArrayList<Equipo>
    private lateinit var equipoAdapter: EquipoAdapter
    private val apiService = RetrofitClient.retrofit.create(ApiService::class.java)
    private val call = apiService.getEquipos()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEquiposBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCrearEquipo.setOnClickListener {
            val intent = Intent(requireActivity(), DetailActivity::class.java)
            val bundle = Bundle().apply {
                putBoolean("nuevoEquipo", true)
            }
            intent.putExtras(bundle)
            startActivity(intent)
        }
        equipoAdapter = EquipoAdapter(ArrayList(), requireContext())
        listaEquipo = ArrayList()
        equipoAdapter = EquipoAdapter(listaEquipo, requireContext())

        binding.equiposRecyclerView.adapter = equipoAdapter
        binding.equiposRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        call.enqueue(object : Callback<List<Equipo>> {
            override fun onResponse(call: Call<List<Equipo>>, response: Response<List<Equipo>>) {
                if (response.isSuccessful) {
                    val equipos = response.body()
                    if (equipos != null) {
                        //LISTA DE EQUIPOS RECIBIDA
                        Log.d("Equipos", equipos.toString())

                        listaEquipo = equipos as ArrayList<Equipo>
                        equipoAdapter.updateList(listaEquipo)

                        equipoAdapter.setOnItemClickListener(object :
                            EquipoAdapter.OnItemClickListener {
                            override fun onItemClick(equipo: Equipo) {
                                val intent = Intent(requireActivity(), DetailActivity::class.java)
                                intent.putExtra("equipo", equipo)
                                startActivity(intent)
                            }
                        })
                    } else {
                        Log.e("Equipos", "la lista de equipos es nula")
                    }
                } else {
                    Log.e("Equipos", "Error en la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Equipo>>, t: Throwable) {
                Log.e("Equipos", "Error al realizar la solicitud: ${t.message}")
            }
        })

        binding.filtrarEquipoEditText.addTextChangedListener { filtro ->
            val equiposFiltrados = listaEquipo.filter { equipo ->
                equipo.nombre.contains(filtro.toString(), ignoreCase = true) || equipo.id.contains(
                    filtro.toString(),
                    ignoreCase = true
                ) || equipo.tipoProducto.contains(filtro.toString(), ignoreCase = true)
            }
            equipoAdapter.updateList(equiposFiltrados as ArrayList<Equipo>)
        }

        binding.filtrarEquipoEditText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                val inputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun actualizarListaEquipos() {
        val call = apiService.getEquipos()
        call.enqueue(object : Callback<List<Equipo>> {
            override fun onResponse(call: Call<List<Equipo>>, response: Response<List<Equipo>>) {
                if (response.isSuccessful) {
                    val equipos = response.body()
                    if (equipos != null) {
                        // Aquí debes actualizar tu adaptador con la nueva lista de equipos
                        equipoAdapter.updateList(equipos)
                    }
                } else {
                    Toast.makeText(context, "Error al obtener la lista de equipos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Equipo>>, t: Throwable) {
                Toast.makeText(context, "Fallo al obtener la lista de equipos", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        binding.filtrarEquipoEditText.setText("")
        actualizarListaEquipos()
    }
}
