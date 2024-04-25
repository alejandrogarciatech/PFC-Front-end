package com.pfc.android.revisionesapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pfc.android.revisionesapp.interfaces.ApiService
import com.pfc.android.revisionesapp.modelos.Equipo
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.E

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.39:8080/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
class EquiposActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipos)

        val equiposRecyclerView: RecyclerView = findViewById(R.id.equiposRecyclerView)
        val layoutManager = GridLayoutManager(this, 1)
        equiposRecyclerView.layoutManager = layoutManager

        val apiService = RetrofitClient.retrofit.create(ApiService::class.java)
        val call = apiService.getEquipos()

        call.enqueue(object : Callback<List<Equipo>>{
            override fun onResponse(call: Call<List<Equipo>>, response: Response<List<Equipo>>) {
                if (response.isSuccessful){
                    val equipos = response.body()
                    if (equipos != null){
                        //LISTA DE EQUIPOS RECIBIDA
                        Log.d("Equipos", equipos.toString())

                        val equiposAdapter = EquiposAdapter(equipos)
                        equiposRecyclerView.adapter = equiposAdapter

                        equiposAdapter.setOnItemClickListener(object : EquiposAdapter.OnItemClickListener {
                            override fun onItemClick(equipo: Equipo) {
                                // Crear un Intent para abrir la actividad de detalles del equipo
                                val intent = Intent(this@EquiposActivity, DetalleEquipoActivity::class.java)

                                // Pasar el objeto Equipo como extra en el intent
                                intent.putExtra("equipo", Bundle())

                                // Iniciar la actividad de detalles del equipo
                                startActivity(intent)
                            }
                        })
                    }else {
                        Log.e("Equipos", "la lista de equipos es nula")
                    }
                }else{
                    Log.e("Equipos", "Error en la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Equipo>>, t: Throwable){
                Log.e("Equipos", "Error al realizar la solicitud: ${t.message}")
            }
        })
    }
}

