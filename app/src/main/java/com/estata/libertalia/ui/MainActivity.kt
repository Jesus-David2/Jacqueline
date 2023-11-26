package com.estata.libertalia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.estata.libertalia.R
import com.estata.libertalia.databinding.ActivityInicioBinding
import com.estata.libertalia.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Matcher
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //1 segundo de espera para que se vea el splashscreen
        Thread.sleep(1000)

        //Regresa al tema principal
        setTheme(R.style.Theme_Libertalia)

        setContentView(ActivityMainBinding.inflate(layoutInflater).also { binding = it }.root)

        iniciar()

        //Ocultar action bar
        this.supportActionBar?.hide()
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    private fun iniciar() {
        //Inicializacion de elementos de la vista
        val edtEmail: EditText = binding.edtGmail
        val edtConteaseña: EditText = binding.edtContrasena
        val btnRegistrar: Button = binding.btnRegistrar
        val btnLogear: Button = binding.btnLogin
        val email = edtEmail.text
        val contraseña = edtConteaseña.text

        //Registro
        btnRegistrar.setOnClickListener{
            if (email.isNotEmpty() && contraseña.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.toString(), contraseña.toString()).addOnCompleteListener{
                    if (it.isSuccessful) {
                        change()
                    } else {
                        if(contraseña.toString().length <6) {
                            showAlertPassword()
                        }
                        if (!validarEmail(email.toString())) {
                            showAlertEmail()
                        }
                    }
                }.addOnFailureListener {
                    showAlertRepeat()
                }

            } else {
                showAlertNotEntry()
            }
        }

        //Loguin
        btnLogear.setOnClickListener{
            if (email.isNotEmpty() && contraseña.isNotEmpty()) {
                if (email.toString() == "libertalia@gmail.com" && contraseña.toString() == "libertalia") {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email.toString(), contraseña.toString()).addOnCompleteListener {
                        if (it.isSuccessful) {
                            change()
                        } else {
                            showAlertEntryFailed()
                        }
                    }
                } else {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email.toString(), contraseña.toString()).addOnCompleteListener {
                        if (it.isSuccessful) {
                            change()
                        } else {
                            showAlertEntryFailed()
                        }
                    }
                }
            } else {
                showAlertNotEntry()
            }
        }
    }

    //Cambiar
    fun change() {
        var intent = Intent(this, InicioActivity::class.java)
        startActivity(intent)
    }

    //Validar email
    private fun validarEmail(texto: String): Boolean {
        val patern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        var comparator: Matcher = patern.matcher(texto)
        return comparator.find()
    }

    //Error en el email
    fun showAlertEmail() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("El email debe de seguir el formato 'correo@gmail.com'")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    //Error en la contraseña
    fun showAlertPassword() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("La contraseña debe de tener más de 6 caracteres")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    //Error si algun campo esta vacio
    fun showAlertNotEntry() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Uno de los campos esta vacio")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    //Error si el usuario ya esta registrado e intenta registrarse de nuevo
    fun showAlertRepeat() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Este correo ya esta registrado")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    //Error al auntentificar
    fun showAlertEntryFailed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("La contraseña o el email no esta correcto")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}