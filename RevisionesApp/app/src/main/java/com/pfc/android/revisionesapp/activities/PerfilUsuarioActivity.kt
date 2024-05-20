package com.pfc.android.revisionesapp.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
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

        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val username = sharedPreferences.getString("username", null)
        val password = sharedPreferences.getString("password", null)

        if (username != null && password != null) {
            viewModel.loadUserData(username)
        }
        Log.d("PerfilUsuarioActivity", "Nombre de usuario: $username, Contraseña: $password")

        // Set up button listeners
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {

            // Borrar los datos del usuario de las SharedPreferences
            val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.remove("username")
            editor.remove("password")
            editor.remove("remember")
            editor.apply()

            // Reiniciar la aplicación en LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }

        val btnEditProfile = findViewById<Button>(R.id.btnEditProfile)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        // Repite para los demás EditText

        btnEditProfile.setOnClickListener {
            etUsername.isEnabled = true
            // Repite para los demás EditText
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