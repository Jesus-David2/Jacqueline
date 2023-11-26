package com.estata.libertalia.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.estata.libertalia.data.modelo.Producto
import com.estata.libertalia.databinding.ProductoCarritoBinding

class ProductoCarritoViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ProductoCarritoBinding.bind(view)

    //(producto: Producto, cantidad: String)
    fun bind (producto: Producto) {
        binding.tvNombre.text = producto.nombre
        //binding.tvNum.text = cantidad
        binding.tvNum.text = "0"
        binding.tvPrecioIndiv.text = producto.precio.toString() + " €"
        //binding.tvPrecioTotal.text = (producto.precio * cantidad.toInt()).toString() + " €"
        binding.tvPrecioTotal.text = "0 €"
    }
}