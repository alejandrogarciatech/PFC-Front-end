package com.pfc.android.revisionesapp.interfaces

import com.pfc.android.revisionesapp.models.Albaran
import com.pfc.android.revisionesapp.models.Equipo
import com.pfc.android.revisionesapp.models.Espacio
import com.pfc.android.revisionesapp.models.Incidencia
import com.pfc.android.revisionesapp.models.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("/login")
    fun login(@Body credentials: Map<String, String>): Call<Usuario>

//    @GET("api/usuarios")
//    fun getUsuarios(): Call<List<Usuario>>

    @GET("api/usuarios/{username}")
    fun getUsuarioByUsername(@Path("username") username: String): Call<Usuario>

    @PUT("api/usuarios/{id}")
    fun updateUsuario(@Path("id") id: Long, @Body usuario: Usuario): Call<Usuario>

//    @POST("api/usuarios/registro")
//    fun login(@Body credentials: Map<String, String>): Call<Usuario>

    @GET("api/equipos")
    fun getEquipos(): Call<List<Equipo>>

    @GET("api/equipos/{id}")
    fun getEquipos(@Path("id") id: String): Call<Equipo>

    @PUT("api/equipos/{id}")
    fun updateEquipo(@Path("id") id: String, @Body equipo: Equipo): Call<Equipo>

    @POST("api/equipos/crear")
    fun createEquipo(@Body equipo: Equipo): Call<Equipo>

    @DELETE("api/equipos/{id}")
    fun deleteEquipo(@Path("id") id: String): Call<Equipo>

    @GET("api/incidencias")
    fun getIncidencias(): Call<List<Incidencia>>

    @GET("api/incidencias/{id}")
    fun getIncidenciaById(@Path("id") id: Int): Call<Incidencia>

    @PUT("api/incidencias/{id}")
    fun updateIncidencia(@Path("id") id: Int, @Body incidencia: Incidencia): Call<Incidencia>

    @POST("api/incidencias/crear")
    fun createIncidencia(@Body incidencia: Incidencia): Call<Incidencia>

    @GET("api/albaranes")
    fun getAlbaranes(): Call<List<Albaran>>

    @GET("api/albaranes/{id}")
    fun getAlbaranById(@Path("id") id: Int): Call<Albaran>

    @GET("api/espacios")
    fun getEspacios(): Call<List<Espacio>>
}