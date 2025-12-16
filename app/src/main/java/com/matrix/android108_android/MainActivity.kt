package com.matrix.android108_android

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.matrix.android108_android.api.RetrofitClient
import com.matrix.android108_android.roomdb.DatabaseClient

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        DatabaseClient.init(this)
        RetrofitClient.init(this)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setupWithNavController(navController)

        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val isLogged = sharedPref.getBoolean("isLogged", false)

       if(isLogged){
           navGraph.setStartDestination(R.id.productsFragment)
       }
        else {
            navGraph.setStartDestination(R.id.loginFragment)
       }
        navController.graph = navGraph

   }
}

