package com.pfc.android.revisionesapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Aquí se establece activity_main.xml como el diseño de contenido de MainActivity
    }

    fun irAEquiposActivity(view: View) {
        val intent = Intent(this, EquiposActivity::class.java)
        startActivity(intent)
    }
}