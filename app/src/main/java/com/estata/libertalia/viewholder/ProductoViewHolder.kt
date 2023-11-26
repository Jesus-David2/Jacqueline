package com.estata.libertalia.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.estata.libertalia.data.modelo.Producto
import com.estata.libertalia.data.repositorio.FirebaseRepos
import com.estata.libertalia.databinding.ProductoAdminBinding

class ProductoViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ProductoAdminBinding.bind(view)
    private val repo = FirebaseRepos()

    fun bind (producto: Producto, listaProductos: ArrayList<Producto>) : ArrayList<Producto> {
        binding.tvNombre.text = producto.nombre

        binding.ivBorrar.setOnClickListener {
            repo.deleteProducto(producto.id)
        }

        return listaProductos
    }
}