package com.estata.libertalia.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.estata.libertalia.data.modelo.Pedido
import com.estata.libertalia.databinding.PedidoEstadoBinding

class PedidoEstadoViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = PedidoEstadoBinding.bind(view)

    fun bind (pedido: Pedido) {
        binding.tvId.text = pedido.id
        binding.tvEstado.text = pedido.estado
        binding.tvNumProd.text = pedido.totalProductos
        binding.tvTotal.text = pedido.precioTotal + " €"
    }
}