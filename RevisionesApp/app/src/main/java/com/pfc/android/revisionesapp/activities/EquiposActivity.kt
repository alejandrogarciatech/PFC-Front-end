package com.pfc.android.revisionesapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pfc.android.revisionesapp.adapters.EquipoAdapter
import com.pfc.android.revisionesapp.databinding.ActivityEquiposBinding
import com.pfc.android.revisionesapp.interfaces.ApiService
import com.pfc.android.revisionesapp.models.Equipo
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.39:8080/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

class EquiposActivity: AppCompatActivity() {

    private lateinit var binding: ActivityEquiposBinding
    private lateinit var listaEquipo: ArrayList<Equipo>
    private lateinit var adaptadorEquipoAdapter: EquipoAdapter
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = ActivityEquiposBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listaEquipo = ArrayList()
        adaptadorEquipoAdapter = EquipoAdapter(listaEquipo, this)

        binding.equiposRecyclerView.adapter = adaptadorEquipoAdapter
        binding.equiposRecyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        val apiService = RetrofitClient.retrofit.create(ApiService::class.java)
        val call = apiService.getEquipos()

        call.enqueue(object : Callback<List<Equipo>> {
            override fun onResponse(call: Call<List<Equipo>>, response: Response<List<Equipo>>) {
                if (response.isSuccessful) {
                    val equipos = response.body()
                    if (equipos != null) {
                        //LISTA DE EQUIPOS RECIBIDA
                        Log.d("Equipos", equipos.toString())

                        adaptadorEquipoAdapter = EquipoAdapter(equipos as ArrayList<Equipo>, this@EquiposActivity)
                        binding.equiposRecyclerView.adapter = adaptadorEquipoAdapter

                        adaptadorEquipoAdapter.setOnItemClickListener(object : EquipoAdapter.OnItemClickListener {
                            override fun onItemClick(equipo: Equipo) {
                                // Crear un Intent para abrir la actividad de detalles del equipo
                                val intent = Intent(this@EquiposActivity, EquipoDetailActivity::class.java)

                                // Pasar el objeto Equipo como extra en el intent
                                intent.putExtra("equipo", equipo)

                                // Iniciar la actividad de detalles del equipo
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
    }
}