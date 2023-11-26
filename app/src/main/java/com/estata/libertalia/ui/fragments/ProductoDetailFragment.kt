package com.estata.libertalia.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import com.estata.libertalia.R
import com.estata.libertalia.data.modelo.Producto
import com.squareup.picasso.Picasso

class ProductoDetailFragment : Fragment() {
    private var producto : Producto? = null
    var cantidad: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            producto = it.getSerializable("productos") as Producto
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_producto_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciar(view)
    }

    private fun iniciar(view: View) {
        //Inicializacion de elementos de la vista
        val ivImagen: ImageView = view.findViewById(R.id.ivImagen)
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecio)
        val desplTallas: Spinner = view.findViewById(R.id.desplTallas)
        val desplColor: Spinner = view.findViewById(R.id.desplColor)
        val ivMenos: ImageView = view.findViewById(R.id.ivMenos)
        val tvContador: TextView = view.findViewById(R.id.tvContador)
        val ivMas: ImageView = view.findViewById(R.id.ivMas)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcion)
        val btnAñadir: Button = view.findViewById(R.id.btnAñadir)

        //Insertar valores en los campos
        tvNombre.setText(producto?.nombre)
        tvPrecio.setText(producto?.precio.toString() + " €")
        tvDescripcion.setText(producto?.descipcion)

        //Insertar imagen
        Picasso.get().load(producto!!.imagenes[0]).into(ivImagen)

        //Insertar menus desplegable de tallas
        val tallas: ArrayList<String> = ArrayList()

        for (talla in producto!!.tallas) {
            tallas.add(talla)
        }

        val adaptTallas = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tallas)
        desplTallas.adapter = adaptTallas

        //Insertar menus desplegable de colores
        val colores: ArrayList<String> = ArrayList()

        for (color in producto!!.colores) {
            colores.add(color)
        }

        val adapColo = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, colores)
        desplColor.adapter = adapColo

        //Contador
        var contador: Int = tvContador.text.toString().toInt()

        ivMenos.setOnClickListener {
            contador--
            if (contador < 0) {
                contador = 0
                tvContador.setText(contador.toString())
            } else {
                tvContador.setText(contador.toString())
            }
        }

        ivMas.setOnClickListener {
            contador++
            tvContador.setText(contador.toString())
        }

        //Listener para añadir el producto al carrito
        btnAñadir.setOnClickListener{
            val bundle = Bundle()
            bundle.putSerializable("prodCarrito", producto)
            //bundle.putSerializable("cantidad", cantidad)

            if (contador > 0) {
                view?.findNavController()?.navigate(R.id.carritoFragment, bundle)
            } else {
                Toast.makeText(requireContext(), "Tienes que añadir al menos uno", Toast.LENGTH_SHORT).show()
            }
        }
    }

}