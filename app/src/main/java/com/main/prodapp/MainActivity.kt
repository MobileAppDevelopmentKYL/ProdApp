package com.main.prodapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.FirebaseFirestore
import com.main.prodapp.database.TodoData
import com.main.prodapp.database.TodoListDatabase
import com.main.prodapp.databinding.ActivityMainBinding
import com.main.prodapp.helpers.FirebaseService
import kotlinx.coroutines.launch


private const val TAG = "MainActivity"

class MainActivity : LocaleActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var database: TodoListDatabase
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Start onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.signInFragment || destination.id == R.id.signUpFragment) {
                binding.bottomNavView.visibility = View.GONE
            } else {
                binding.bottomNavView.visibility = View.VISIBLE
            }
        }

        binding.bottomNavView.setOnItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, navController)
        }
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "Start onStart")
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "Start onResume")
    }

    override fun onPause() {
        super.onPause()

        Log.d(TAG, "Start onPause")
    }

    override fun onStop() {
        super.onStop()

//        lifecycleScope.launch {
//            database.todoListDao().deleteAll()
//        }

        Log.d(TAG, "Start onStop")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "Start onDestroy")
    }
}

