package com.example.task

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.task.data.Pref
import com.example.task.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = Pref(this)
        val auth = FirebaseAuth.getInstance()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        if (!pref.isOnBoardingSeen())
            navController.navigate(R.id.navigation_on_boarding)

        if (auth.currentUser==null){
            navController.navigate(R.id.authFragment)
        }

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_profile,
                R.id.navigation_task

            )
        )
        val navFragments = arrayListOf(
            R.id.navigation_home,
            R.id.navigation_dashboard,
            R.id.navigation_notifications,
            R.id.navigation_profile,
            R.id.navigation_task
        )
        navController.addOnDestinationChangedListener { _, destination, _ ->
            navView.isVisible = navFragments.contains(destination.id)
            if (destination.id == R.id.navigation_on_boarding || destination.id == R.id.authFragment) {
                supportActionBar?.hide()
            } else supportActionBar?.show()
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        FirebaseMessaging.getInstance().token.addOnCompleteListener{
            if (it.isSuccessful){
                Log.e("tokenMessage","onCreate${it.result}")
            }
        }
    }
}