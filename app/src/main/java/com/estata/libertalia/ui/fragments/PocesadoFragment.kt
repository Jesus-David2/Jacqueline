package com.estata.libertalia.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.estata.libertalia.R

class PocesadoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pocesado, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciar(view)
    }

    private fun iniciar(view: View) {
        //Inicializacion del elemento de la vista
        val btnRegresar: Button = view.findViewById(R.id.btnRegresar)

        //Listener para cambiar a la pantalla
        btnRegresar.setOnClickListener{
            view?.findNavController()?.navigate(R.id.catalogoFragment)
        }

    }
}