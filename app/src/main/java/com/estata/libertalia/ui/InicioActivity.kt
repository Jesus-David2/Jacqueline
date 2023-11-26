package com.estata.libertalia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.estata.libertalia.R
import com.estata.libertalia.databinding.ActivityInicioBinding
import com.estata.libertalia.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class InicioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInicioBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityInicioBinding.inflate(layoutInflater).also { binding = it }.root)
        setSupportActionBar(binding.toolbar)

        val menulateralLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.catalogoFragment,
            R.id.historialFragment,
            R.id.estadoFragment,
            R.id.contactoFragment,
        ),
            menulateralLayout
        )

        NavigationUI.setupWithNavController(navView, navController)
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)

        if (FirebaseAuth.getInstance().currentUser?.uid == "Am6yqgFvBCPFjeZ4Ju2sI6deU6q1") {
            navController.navigate(R.id.adminFragment)
        }
    }

    override fun onBackPressed() {
        val fragmento = findNavController(R.id.nav_host_fragment)

        if (fragmento != null && fragmento.currentDestination?.id == R.id.catalogoFragment ||
            fragmento != null && fragmento.currentDestination?.id == R.id.adminFragment) {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            super.onBackPressed()
        }
    }
}