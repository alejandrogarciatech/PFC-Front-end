package com.pfc.android.revisionesapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pfc.android.revisionesapp.R
import com.pfc.android.revisionesapp.models.Albaran

class AlbaranesAdapter(var lista: ArrayList<Albaran>, var context: Context) :
    RecyclerView.Adapter<AlbaranesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id: TextView = itemView.findViewById(R.id.idAlbaranTextView)
        var fechaSalida: TextView = itemView.findViewById(R.id.salidaAlbaranTextView)
        var fechaEntrada: TextView = itemView.findViewById(R.id.entradaAlbaranTextView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_albaran, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val albaran: Albaran = lista[position]
        viewHolder.id.text = albaran.id
        viewHolder.fechaSalida.text = albaran.fechaSalida
        viewHolder.fechaEntrada.text = albaran.fechaEntrada

//        viewHolder.itemView.setOnClickListener {
//            listener.onItemClick(albaran)
//        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }

//    interface OnItemClickListener {
//        fun onItemClick(albaran: Albaran)
//    }
//
//    lateinit var listener: OnItemClickListener
//
//    fun setOnItemClickListener(listener: OnItemClickListener) {
//        this.listener = listener
//    }

    fun updateList(lista: List<Albaran>?) {
        this.lista = lista as ArrayList<Albaran>
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        lista.removeAt(position)
        notifyItemRemoved(position)
    }
}