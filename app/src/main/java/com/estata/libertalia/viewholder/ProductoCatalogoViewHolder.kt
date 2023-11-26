package com.estata.libertalia.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.estata.libertalia.data.modelo.Producto
import com.estata.libertalia.databinding.ProductoCatalogoBinding
import com.squareup.picasso.Picasso

class ProductoCatalogoViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ProductoCatalogoBinding.bind(view)

    fun bind (producto: Producto, listaProductos: ArrayList<Producto>) : ArrayList<Producto> {
        binding.tvNombre.text = producto.nombre
        binding.tvCategorias.text = producto.categorias
        binding.tvPrecio.text = producto.precio.toString() + "€"
        Picasso.get().load(producto.imagenes[0]).into(binding.ivImagen)

        return listaProductos
    }
}