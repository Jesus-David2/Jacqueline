package com.estata.libertalia.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.estata.libertalia.R
import com.estata.libertalia.data.modelo.Pedido
import com.estata.libertalia.viewholder.PedidoHistorialViewHolder

class PedidoHistorialAdapter(private var pedidoList: ArrayList<Pedido>): RecyclerView.Adapter<PedidoHistorialViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoHistorialViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PedidoHistorialViewHolder(layoutInflater.inflate(R.layout.pedido_historial, parent, false))
    }

    override fun onBindViewHolder(holder: PedidoHistorialViewHolder, position: Int) {
        val item = pedidoList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = pedidoList.size

    fun updateData(dataList: ArrayList<Pedido>) {
        this.pedidoList = dataList
        notifyDataSetChanged()
    }
}