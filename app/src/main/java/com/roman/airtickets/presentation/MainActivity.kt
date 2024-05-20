package com.roman.airtickets.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.roman.airtickets.R
import com.roman.airtickets.databinding.ActivityMainBinding
import com.roman.airtickets.presentation.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences=getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        if (sharedPreferences.contains(SHARED_PREFERENCE_TOWN))
        {
            val text=sharedPreferences.getString(SHARED_PREFERENCE_TOWN,"").toString()
            viewModel.setRoutFrom(text)

        }
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(binding.bottomNavigation, navController)


    }
 companion object
 {
     const val SHARED_PREFERENCE_NAME="name_shared_pref"
     const val SHARED_PREFERENCE_TOWN="town_name"
 }

}