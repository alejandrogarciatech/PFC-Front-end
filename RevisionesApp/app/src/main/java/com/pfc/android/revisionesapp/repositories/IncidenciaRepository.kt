package com.pfc.android.revisionesapp.repositories

import android.content.Context
import android.util.Log
import com.pfc.android.revisionesapp.interfaces.ApiService
import com.pfc.android.revisionesapp.models.Incidencia
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class IncidenciaRepository (private val context: Context){

    private val apiService: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.37:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

//    fun getIncidencias(incidenciaId: Int, onSuccess: (Incidencia) -> Unit, onError: (String) -> Unit) {
//        apiService.getIncidencias(incidenciaId).enqueue(object : Callback<Incidencia> {
//            override fun onResponse(call: Call<Incidencia>, response: Response<Incidencia>) {
//                if (response.isSuccessful) {
//                    val incidencia = response.body()
//                    if (incidencia != null) {
//                        onSuccess(incidencia)
//                    } else {
//                        onError("La incidencia con ID $incidenciaId no existe")
//                    }
//                } else {
//                    Log.e(
//                        "API ERROR",
//                        "Response Code: " + response.code() + " Message: " + response.message()
//                    )
//                    onError("ERROR al obtener la incidencia")
//                }
//            }
//
//            override fun onFailure(call: Call<Incidencia>, t: Throwable) {
//                onError("FALLO al obtener la incidencia")
//            }
//        })
//    }
}