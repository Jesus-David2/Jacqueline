package com.estata.libertalia.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estata.libertalia.R
import com.estata.libertalia.data.adapter.ProductoAdminAdapter
import com.estata.libertalia.data.modelo.Producto
import com.estata.libertalia.data.repositorio.FirebaseRepos
import com.estata.libertalia.viewholder.ProductoViewHolder
import kotlinx.coroutines.launch

class AdminFragment : Fragment(), com.estata.libertalia.data.interfaces.OnItemClickListener {
    lateinit var recview: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Inicializacion de elemento de la vista
        val btnNuevo: Button = view.findViewById(R.id.btnNuevo)
        val btnRecargar: Button = view.findViewById(R.id.btnRecargar)

        //Listener para cambiar a la pantalla de nuevo producto
        btnNuevo.setOnClickListener{
            view?.findNavController()?.navigate(R.id.productoNuevoFragment)
        }

        //Listener para actualizar la vista
        btnRecargar.setOnClickListener{
            view?.findNavController()?.navigate(R.id.adminFragment)
        }

        iniciar(view)
    }

    //Listener para pasar los datos del producto a la pantalla de edictar producto
    override fun onItemClick(producto: Producto) {
        val bundel = Bundle()
        bundel.putSerializable("productos", producto)

        view?.findNavController()?.navigate(R.id.editarProductoFragment, bundel)
    }

    fun iniciar(view: View) {
        //Inicializacion de elemento de la vista
        recview = view.findViewById(R.id.rvProductos)

        //Recycler view
        var repo = FirebaseRepos()
        var prodList = arrayListOf<Producto>()

        recview.setHasFixedSize(true)
        recview.visibility = View.VISIBLE
        recview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        lifecycleScope.launch{
            prodList = repo.getAllProductos() as ArrayList<Producto>
            var adapter = ProductoAdminAdapter(prodList, this@AdminFragment)

            recview.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }
}