package com.pfc.android.revisionesapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.pfc.android.revisionesapp.interfaces.ApiService
import com.pfc.android.revisionesapp.modelos.Equipo
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.39:8080/equipos/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

class EquiposActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipos)

        val apiService = RetrofitClient.retrofit.create(ApiService::class.java)
        val call = apiService.getEquipos()

        call.enqueue(object : Callback<List<Equipo>>{
            override fun onResponse(call: Call<List<Equipo>>, response: Response<List<Equipo>>) {
                if (response.isSuccessful){
                    val equipos = response.body()
                    //LISTA DE EQUIPOS RECIBIDA
                    Log.d("Equipos", equipos.toString())
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

