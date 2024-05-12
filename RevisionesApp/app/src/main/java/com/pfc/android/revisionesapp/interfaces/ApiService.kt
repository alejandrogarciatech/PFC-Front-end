package com.pfc.android.revisionesapp.interfaces

import com.pfc.android.revisionesapp.models.Equipo
import com.pfc.android.revisionesapp.models.Incidencia
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("api/equipos")
    fun getEquipos(): Call<List<Equipo>>

    @GET("api/incidencias")
    fun getIncidencias(): Call<List<Incidencia>>

    @GET("api/equipos/{id}")
    fun getEquipos(@Path("id") id: String): Call<Equipo>

    @GET("api/incidencias/{id}")
    fun getIncidencias(@Path("id") id: String): Call<Incidencia>

    @PUT("api/equipos/{id}")
    fun updateEquipo(@Path("id") id: String, @Body equipo: Equipo): Call<Equipo>

    @POST("api/equipos/crear")
    fun createEquipo(@Body equipo: Equipo): Call<Equipo>
}