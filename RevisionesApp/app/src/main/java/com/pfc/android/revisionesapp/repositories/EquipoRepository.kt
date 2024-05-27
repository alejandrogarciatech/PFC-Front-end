package com.pfc.android.revisionesapp.repositories

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.pfc.android.revisionesapp.interfaces.ApiService
import com.pfc.android.revisionesapp.models.Equipo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EquipoRepository(private val context: Context) {

    private val retrofit = com.pfc.android.revisionesapp.fragments.RetrofitClient.instance
    private val apiService = retrofit.create(com.pfc.android.revisionesapp.interfaces.ApiService::class.java)

    fun getEquipo(equipoId: String, onSuccess: (Equipo) -> Unit, onError: (String) -> Unit) {
        apiService.getEquipos(equipoId).enqueue(object : Callback<Equipo> {
            override fun onResponse(call: Call<Equipo>, response: Response<Equipo>) {
                if (response.isSuccessful) {
                    val equipo = response.body()
                    if (equipo != null) {
                        onSuccess(equipo)
                    } else {
                        onError("El equipo con ID $equipoId no existe")
                    }
                } else {
                    Log.e(
                        "API ERROR",
                        "Response Code: " + response.code() + " Message: " + response.message()
                    )
                    onError("ERROR al obtener el equipo")
                }
            }

            override fun onFailure(call: Call<Equipo>, t: Throwable) {
                onError("FALLO al obtener el equipo")
            }
        })
    }

    fun updateEquipo(equipo: Equipo) {
        apiService.updateEquipo(equipo.id, equipo).enqueue(object : Callback<Equipo> {
            override fun onResponse(call: Call<Equipo>, response: Response<Equipo>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Equipo actualizado con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error al actualizar el equipo. Código de estado: ${response.code()}, Mensaje: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Equipo>, t: Throwable) {
                Toast.makeText(context, "Fallo al actualizar el equipo", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun createEquipo(equipo: Equipo) {
        apiService.createEquipo(equipo).enqueue(object : Callback<Equipo> {
            override fun onResponse(call: Call<Equipo>, response: Response<Equipo>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Equipo creado con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error al crear el equipo. Código de estado: ${response.code()}, Mensaje: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Equipo>, t: Throwable) {
                Toast.makeText(context, "Fallo al crear el equipo", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
