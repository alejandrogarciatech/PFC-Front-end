package com.pfc.android.revisionesapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.pfc.android.revisionesapp.modelos.Equipo

class DetalleEquipoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_equipo)

        // Obtener el objet Equipo de los extras del intent
        val equipo = intent.getBundleExtra("equipo")

        // Verificar si el objeto Equipo no es nulo
        if (equipo != null) {
            // Mostrar los detalles del equipo en la interfaz de usuario
            // Por ejemplo:
            nombreEquipoTextView.text = equipo.nombre
            marcaTextView.text = equipo.marca
            // Completa con otros attributes del equipo según sea necesario
        } else {
            // Manejar el caso en el que no se pueda obtener el objeto Equipo
            Log.e("DetalleEquipoActivity", "No se pudo obtener el objeto Equipo de los extras del intent")
        }
    }
}
