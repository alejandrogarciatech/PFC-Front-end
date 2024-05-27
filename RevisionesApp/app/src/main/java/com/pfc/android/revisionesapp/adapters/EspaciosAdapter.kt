package com.pfc.android.revisionesapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pfc.android.revisionesapp.R
import com.pfc.android.revisionesapp.databinding.CardEspacioBinding
import com.pfc.android.revisionesapp.databinding.CardIncidenciaBinding
import com.pfc.android.revisionesapp.models.Albaran
import com.pfc.android.revisionesapp.models.Espacio
import com.pfc.android.revisionesapp.models.Incidencia

class EspaciosAdapter (var lista: ArrayList<Espacio>, var context: Context) :
    RecyclerView.Adapter<EspaciosAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CardEspacioBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (espacio: Espacio) {
            binding.itemNombre.text = espacio.nombre
            binding.itemUbicacion.text = espacio.ubicacion
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val binding: CardEspacioBinding = CardEspacioBinding.inflate(LayoutInflater.from(context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val espacio: Espacio = lista[position]
        viewHolder.bind(espacio)

        viewHolder.itemView.setOnClickListener {
            listener.onItemClick(espacio)
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
        fun onItemClick(espacio: Espacio)
    }

    fun updateList(lista: List<Espacio>?) {
        this.lista.clear()
        if (lista != null) {
            this.lista.addAll(lista)
        }
        notifyDataSetChanged()
    }
}