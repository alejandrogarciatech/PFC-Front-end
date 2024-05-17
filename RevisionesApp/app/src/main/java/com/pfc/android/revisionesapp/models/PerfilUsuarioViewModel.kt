package com.pfc.android.revisionesapp.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.pfc.android.revisionesapp.repositories.UsuarioRepositorio

class PerfilUsuarioViewModel : ViewModel() {

    private val usuarioRepositorio = UsuarioRepositorio()

    private val _usuario = MutableLiveData<Usuario>()
    val usuario: LiveData<Usuario> get() = _usuario

    val userId = MutableLiveData<String>()
    val userName = MutableLiveData<String>()
    val userSurname = MutableLiveData<String>()
    val userBirthdate = MutableLiveData<String>()
    val userRole = MutableLiveData<String>()
    val userPhone = MutableLiveData<String>()
    val userEmail = MutableLiveData<String>()
    val userUsername = MutableLiveData<String>()
    val userPassword = MutableLiveData<String>()

    fun loadUserData(username: String, password: String) {
        usuarioRepositorio.getUserByUsernameAndPassword(username, password, { usuario ->
            _usuario.value = usuario
            userId.value = usuario.id.toString()
            userName.value = usuario.nombre
            userSurname.value = usuario.apellido
            userBirthdate.value = usuario.fechaNacimiento
            userRole.value = usuario.rol
            userPhone.value = usuario.telefono
            userEmail.value = usuario.email
            userUsername.value = usuario.username
            userPassword.value = usuario.password

            // Log para verificar que los datos del usuario se cargaron correctamente
            Log.d("PerfilUsuarioViewModel", "Usuario cargado: $usuario")

        }, { error ->
            // Log para verificar el error
            Log.d("PerfilUsuarioViewModel", "Error al cargar el usuario: $error")
        })
    }

    fun saveUserData() {
        val usuario = _usuario.value?.copy(
            nombre = userName.value ?: "",
            apellido = userSurname.value ?: "",
            fechaNacimiento = userBirthdate.value ?: "",
            telefono = userPhone.value ?: "",
            email = userEmail.value ?: "",
            username = userUsername.value ?: "",
            password = userPassword.value ?: ""
        )
        if (usuario != null) {
            usuarioRepositorio.updateUsuario(usuario)
            _usuario.value = usuario!!
        }
    }
}