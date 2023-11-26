package com.estata.libertalia.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estata.libertalia.R
import com.estata.libertalia.data.adapter.PedidoEstadoAdapter
import com.estata.libertalia.data.adapter.ProductoCarritoAdapter
import com.estata.libertalia.data.modelo.Pedido
import com.estata.libertalia.data.modelo.Producto
import com.estata.libertalia.data.repositorio.FirebaseRepos
import kotlinx.coroutines.launch

class CarritoFragment : Fragment() {
    lateinit var recview: RecyclerView
    private var producto : Producto? = null
    var cantidad: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            producto = it.getSerializable("prodCarrito") as Producto
            //cantidad = it.getSerializable("cantidad") as String
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_carrito, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciar(view)
    }

    private fun iniciar(view: View) {
        //Inicializacion de elementos de la vista
        val btnComprar: Button = view.findViewById(R.id.btnComprar)
        val tvPrecioTotal: TextView = view.findViewById(R.id.tvPrecioTotal)
        recview = view.findViewById(R.id.rvLista)

        /*Recycler view
        var repo = FirebaseRepos()
        var prodList = arrayListOf<Producto>()

        recview.setHasFixedSize(true)
        recview.visibility = View.VISIBLE
        recview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        lifecycleScope.launch{
            prodList = repo.getProducto(producto!!.id) as ArrayList<Producto>
            //var adapter = ProductoCarritoAdapter(prodList, cantidad)
            var adapter = ProductoCarritoAdapter(prodList)

            recview.adapter = adapter
        }*/

        //Listener para cambiar a la pantalla de procesado
        btnComprar.setOnClickListener{
            view?.findNavController()?.navigate(R.id.pocesadoFragment)
        }
    }
}