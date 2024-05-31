package com.pfc.android.revisionesapp.repositories

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.pfc.android.revisionesapp.interfaces.ApiService
import com.pfc.android.revisionesapp.models.Incidencia
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IncidenciaRepository (private val context: Context){

    private val retrofit = com.pfc.android.revisionesapp.fragments.RetrofitClient.instance
    private val apiService = retrofit.create(ApiService::class.java)

    fun getIncidenciaById(incidenciaId: Int, onSuccess: (Incidencia) -> Unit, onError: (String) -> Unit) {
        apiService.getIncidenciaById(incidenciaId).enqueue(object : Callback<Incidencia> {
            override fun onResponse(call: Call<Incidencia>, response: Response<Incidencia>) {
                if (response.isSuccessful) {
                    val incidencia = response.body()
                    if (incidencia != null) {
                        onSuccess(incidencia)
                    } else {
                        onError("La incidencia con ID $incidenciaId no existe")
                    }
                } else {
                    Log.e(
                        "API ERROR",
                        "Response Code: " + response.code() + " Message: " + response.message()
                    )
                    onError("ERROR al obtener la incidencia")
                }
            }

            override fun onFailure(call: Call<Incidencia>, t: Throwable) {
                onError("FALLO al obtener la incidencia")
            }
        })
    }

    fun updateIncidencia(incidencia: Incidencia) {
        apiService.updateIncidencia(incidencia.id, incidencia).enqueue(object : Callback<Incidencia> {
            override fun onResponse(call: Call<Incidencia>, response: Response<Incidencia>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Incidencia actualizada con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error al actualizar la incidencia. Código de estado: ${response.code()}, Mensaje: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Incidencia>, t: Throwable) {
                Toast.makeText(context, "Fallo al actualizar la incidencia", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun createIncidencia(incidencia: Incidencia) {
        apiService.createIncidencia(incidencia).enqueue(object : Callback<Incidencia> {
            override fun onResponse(call: Call<Incidencia>, response: Response<Incidencia>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Incidencia creada con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error al crear la incidencia. Código de estado: ${response.code()}, Mensaje: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Incidencia>, t: Throwable) {
                Toast.makeText(context, "Fallo al crear la incidencia", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun deleteIncidencia(incidenciaId: Int) {
        apiService.deleteIncidencia(incidenciaId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Incidencia eliminada con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error al eliminar la incidencia. Código de estado: ${response.code()}, Mensaje: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(context, "Fallo al eliminar la incidencia", Toast.LENGTH_SHORT).show()
            }
        })
    }
}