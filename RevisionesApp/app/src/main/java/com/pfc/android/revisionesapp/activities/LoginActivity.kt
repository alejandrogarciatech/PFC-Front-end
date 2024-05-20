package com.pfc.android.revisionesapp.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.pfc.android.revisionesapp.databinding.ActivityLoginBinding
import com.pfc.android.revisionesapp.fragments.RetrofitClient
import com.pfc.android.revisionesapp.interfaces.ApiService
import com.pfc.android.revisionesapp.models.Usuario
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val retrofit = RetrofitClient.instance.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Verificar si ya existe una sesión iniciada
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("username", null)
        val remember = sharedPreferences.getBoolean("remember", false)

        // Si el usuario ha marcado recordar y ha iniciado sesión, redirigir a MainActivity
        if (remember && savedUsername != null) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Configuración del botón de inicio de sesión
        binding.botonLogin.setOnClickListener {
            try {
                val username = binding.editUser.text.toString()
                val password = binding.editPass.text.toString()

                // Validación de los campos de entrada
                if (username.isBlank() || password.isBlank()) {
                    Snackbar.make(
                        binding.root,
                        "Por favor, rellena todos los campos",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                val credentials = mapOf("username" to username, "password" to password)
                val call = retrofit.login(credentials)

                call.enqueue(object : retrofit2.Callback<Usuario> {
                    override fun onResponse(
                        call: Call<Usuario>,
                        response: Response<Usuario>
                    ) {
                        if (response.isSuccessful) {
                            // Las credenciales son válidas, inicia la siguiente actividad
                            val remember = binding.checkRecordar.isChecked
                            val sharedPreferences: SharedPreferences =
                                getSharedPreferences("MyPrefs", MODE_PRIVATE)
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
//                            if (remember) {
                                // Si el usuario ha marcado "Recordar", guardar el nombre de usuario y el estado de "Recordar"
                                editor.putString("username", username)
                                editor.putString("password", password)
                                editor.putBoolean("remember", true)
//                            } else {
//                                // Si el usuario no ha marcado "Recordar", borrar el nombre de usuario y el estado de "Recordar"
//                                editor.remove("username")
//                                editor.remove("password")
//                                editor.putBoolean("remember", false)
//                            }
                            editor.apply()

                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            intent.putExtra("username", username)
                            intent.putExtra("password", password)
                            finish()
                        } else {
                            // Las credenciales no son válidas, muestra un mensaje de error
                            Snackbar.make(
                                binding.root,
                                "El usuario o la contraseña son incorrectos",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Usuario>, t: Throwable) {
                        // Hubo un error al hacer la solicitud, maneja el error
                        Snackbar.make(
                            binding.root,
                            "Ha ocurrido un error: ${t.message}",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                })
            } catch (e: Exception) {
                // Manejo de errores generales
                Snackbar.make(
                    binding.root,
                    "Ha ocurrido un error: ${e.message}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
}
