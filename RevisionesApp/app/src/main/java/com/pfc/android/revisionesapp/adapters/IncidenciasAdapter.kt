package com.pfc.android.revisionesapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pfc.android.revisionesapp.R
import com.pfc.android.revisionesapp.activities.DetailActivity
import com.pfc.android.revisionesapp.databinding.CardIncidenciaBinding
import com.pfc.android.revisionesapp.models.Incidencia

class IncidenciasAdapter(var lista: ArrayList<Incidencia>, var contexto: Context) :
    RecyclerView.Adapter<IncidenciasAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CardIncidenciaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(incidencia: Incidencia) {
//            Glide.with(binding.itemImagen.context)
//                .load(incidencia.imageUrl) // Asume que 'imageUrl' es la URL de la imagen de la incidencia
//                .placeholder(R.drawable.ic_launcher_foreground) // Imagen de reserva mientras se carga la imagen
//                .into(binding.itemImagen)
            binding.itemTitulo.text = incidencia.id.toString()
            binding.itemDetalle.text = incidencia.descripcion
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val binding: CardIncidenciaBinding = CardIncidenciaBinding.inflate(LayoutInflater.from(contexto), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val incidencia: Incidencia = lista[position]
        viewHolder.bind(incidencia)

        viewHolder.itemView.setOnClickListener {
            listener.onItemClick(incidencia)
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    lateinit var listener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(incidencia: Incidencia)
    }

    fun updateList(lista: List<Incidencia>?) {
        this.lista.clear()
        if (lista != null) {
            this.lista.addAll(lista)
        }
        notifyDataSetChanged()
    }
}