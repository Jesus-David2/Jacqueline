package com.estata.libertalia.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estata.libertalia.R
import com.estata.libertalia.data.adapter.ProductoAdminAdapter
import com.estata.libertalia.data.adapter.ProductoCatalogoAdapter
import com.estata.libertalia.data.modelo.Producto
import com.estata.libertalia.data.repositorio.FirebaseRepos
import kotlinx.coroutines.launch

class CatalogoFragment : Fragment(), com.estata.libertalia.data.interfaces.OnItemClickListener {
    lateinit var repos: FirebaseRepos
    lateinit var recview: RecyclerView
    lateinit var adapter: ProductoCatalogoAdapter
    lateinit var prodList: ArrayList<Producto>

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalogo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Mostrar action bar
        (requireActivity() as AppCompatActivity).supportActionBar?.show()

        //Añadir usuario
        repos = FirebaseRepos()
        repos.addUser(arrayListOf(), requireContext())

        iniciar(view)
    }

    //Listener para pasar los datos del producto a la pantalla de detalle del producto
    override fun onItemClick(producto: Producto) {
        val bundel = Bundle()
        bundel.putSerializable("productos", producto)

        view?.findNavController()?.navigate(R.id.productoDetailFragment, bundel)
    }

    fun iniciar(view: View) {
        //Inicializacion de elementos de la vista
        val busca: SearchView = view.findViewById(R.id.buscador)
        val ivCarrito: ImageView = view.findViewById(R.id.ivCarrito)
        recview = view.findViewById(R.id.rvCatalogo)

        //Listener para cambiar a la pantalla del carrito
        ivCarrito.setOnClickListener {
            view?.findNavController()?.navigate(R.id.carritoFragment)
        }

        //Recycler view
        var repo = FirebaseRepos()
        prodList = arrayListOf<Producto>()

        recview.setHasFixedSize(true)
        recview.visibility = View.VISIBLE
        recview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        lifecycleScope.launch{
            prodList = repo.getAllProductos() as ArrayList<Producto>
            adapter = ProductoCatalogoAdapter(prodList, this@CatalogoFragment)

            recview.adapter = adapter
        }

        //buscador
        busca.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            //mientras esctibe
            override fun onQueryTextChange(query: String): Boolean {
                if (query.isEmpty()) {
                    adapter.updateData(prodList)
                } else {
                    filtrar(query)
                }
                return false
            }

            //cuando termina de escribir
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    adapter.updateData(prodList)
                } else {
                    filtrar(query)
                }
                return false
            }
        })
    }

    fun filtrar(query: String) {
        var listaFiltrada = arrayListOf<Producto>()

        for (prod in prodList) {
            if (prod.categorias.contains(query)) {
                listaFiltrada.add(prod)
            }
        }

        adapter.updateData(listaFiltrada)
    }
}