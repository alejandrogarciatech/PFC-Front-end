package com.pfc.android.revisionesapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.pfc.android.revisionesapp.databinding.FragmentScannerBinding
import com.pfc.android.revisionesapp.interfaces.ApiService
import com.pfc.android.revisionesapp.models.Equipo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path

class ScannerFragment : Fragment() {

    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnScanner.setOnClickListener { initScanner() }
    }

    private fun initScanner() {
        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Escanea el código QR del equipo")
        integrator.setTorchEnabled(true)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show()
            } else {
                val equipoId = result.contents
                getEquipo(equipoId)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun getEquipo(equipoId: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.39:8080/") // Reemplaza con la URL base de tu API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.getEquipos(equipoId).enqueue(object : Callback<Equipo> {
            override fun onResponse(call: Call<Equipo>, response: Response<Equipo>) {
                if (response.isSuccessful) {
                    val equipo = response.body()
                    if (equipo != null) {
                        abrirDetalleEquipo(equipo)
                    } else {
                        Toast.makeText(context, "El equipo con ID $equipoId no existe", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("API ERROR", "Response Code: " + response.code() + " Message: " + response.message())
                    Toast.makeText(context, "ERROR al obtener el equipo", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Equipo>, t: Throwable) {
                Toast.makeText(context, "FALLO al obtener el equipo", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun abrirDetalleEquipo(equipo: Equipo) {
        val intent = Intent(context, EquipoDetailActivity::class.java)
        intent.putExtra("equipo", equipo)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}