package com.estata.libertalia.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estata.libertalia.R
import com.estata.libertalia.data.adapter.PedidoEstadoAdapter
import com.estata.libertalia.data.adapter.PedidoHistorialAdapter
import com.estata.libertalia.data.adapter.ProductoCatalogoAdapter
import com.estata.libertalia.data.modelo.Pedido
import com.estata.libertalia.data.modelo.Producto
import com.estata.libertalia.data.repositorio.FirebaseRepos
import kotlinx.coroutines.launch

class EstadoFragment : Fragment() {
    lateinit var recview: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estado, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iniciar(view)
    }

    fun iniciar(view: View) {
        //Inicializacion de elemento de la vista
        recview = view.findViewById(R.id.rvEstado)

        //Recycler view
        var repo = FirebaseRepos()
        var pedList = arrayListOf<Pedido>()

        recview.setHasFixedSize(true)
        recview.visibility = View.VISIBLE
        recview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        lifecycleScope.launch{
            pedList = repo.getPedidosEstado() as ArrayList<Pedido>
            var adapter = PedidoEstadoAdapter(pedList)

            recview.adapter = adapter
        }
    }
}