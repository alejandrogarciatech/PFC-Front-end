package com.pfc.android.revisionesapp.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.pfc.android.revisionesapp.R
import com.pfc.android.revisionesapp.databinding.ActivityPerfilUsuarioBinding
import com.pfc.android.revisionesapp.models.PerfilUsuarioViewModel

class PerfilUsuarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilUsuarioBinding
    private val viewModel: PerfilUsuarioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Load user data
        val username = intent.getStringExtra("username") ?: ""
        val password = intent.getStringExtra("password") ?: ""

        // Log para verificar el nombre de usuario y la contraseña
        Log.d("PerfilUsuarioActivity", "Nombre de usuario: $username, Contraseña: $password")
        viewModel.loadUserData(username, password)

        // Set up button listeners
        binding.btnLogout.setOnClickListener {
            //findNavController(R.id.navHostFragment).navigate(R.id.action_perfilUsuarioActivity_to_loginActivity)
        }

        binding.btnEditProfile.setOnClickListener {
            viewModel.saveUserData()
        }

        // Observe changes to update UI
        viewModel.usuario.observe(this, Observer { usuario ->
            viewModel.userId.value = usuario.id.toString()
            viewModel.userName.value = usuario.nombre
            viewModel.userSurname.value = usuario.apellido
            viewModel.userBirthdate.value = usuario.fechaNacimiento
            viewModel.userRole.value = usuario.rol
            viewModel.userPhone.value = usuario.telefono
            viewModel.userEmail.value = usuario.email
            viewModel.userUsername.value = usuario.username
            viewModel.userPassword.value = usuario.password
        })
    }
}