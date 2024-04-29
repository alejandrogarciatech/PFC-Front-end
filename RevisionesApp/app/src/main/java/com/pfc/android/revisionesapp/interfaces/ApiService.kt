package com.pfc.android.revisionesapp.interfaces

import com.pfc.android.revisionesapp.models.Equipo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("api/equipos")
    fun getEquipos(): Call<List<Equipo>>

    @GET("api/equipos/{id}")
    fun getEquipos(@Path("id") id: String): Call<Equipo>
}