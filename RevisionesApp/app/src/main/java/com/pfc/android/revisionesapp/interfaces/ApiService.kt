package com.pfc.android.revisionesapp.interfaces

import com.pfc.android.revisionesapp.models.Equipo
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/equipos")
    fun getEquipos(): Call<List<Equipo>>
}