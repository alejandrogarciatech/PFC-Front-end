package com.pfc.android.revisionesapp.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.pfc.android.revisionesapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botonLogin.setOnClickListener {
            try {
                val username = binding.editUser.text.toString()
                val password = binding.editPass.text.toString()

                // Validación de los campos de entrada
                if (username.isBlank() || password.isBlank()) {
                    Snackbar.make(binding.root, "Por favor, rellena todos los campos", Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (username == "Admin" && password == "admin") {
                    val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("username", username)
                    editor.apply()

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Snackbar.make(binding.root, "El usuario o la contraseña son incorrectos", Snackbar.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Manejo de errores generales
                Snackbar.make(binding.root, "Ha ocurrido un error: ${e.message}", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}