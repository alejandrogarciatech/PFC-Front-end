package com.pfc.android.revisionesapp.repositories

import com.pfc.android.revisionesapp.models.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsuarioRepositorio {

    // llamar al objeto retrofit
    private val retrofit = com.pfc.android.revisionesapp.fragments.RetrofitClient.instance
    // llamar a la interfaz
    private val apiService = retrofit.create(com.pfc.android.revisionesapp.interfaces.ApiService::class.java)

    fun getUsuarioByUsername(username: String, onSuccess: (Usuario) -> Unit, onError: (String) -> Unit) {
        val call = apiService.getUsuarioByUsername(username)

        call.enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    val usuario = response.body()
                    if (usuario != null) {
                        onSuccess(usuario)
                    } else {
                        onError("El usuario con nombre de usuario $username no existe")
                    }
                } else {
                    onError("ERROR al obtener el usuario")
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                onError("FALLO al obtener el usuario")
            }
        })
    }

    fun updateUsuario(usuario: Usuario) {
        val call = apiService.updateUsuario(usuario.id, usuario)

        call.enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (!response.isSuccessful) {
                    // Manejar error
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                // Manejar error
            }
        })
    }
}



