package com.example.gps_kotlin

//import HistoryFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.commit
import com.example.gps_kotlin.databinding.ActivityMainBinding
import com.example.gps_kotlin.history.HistoryFragment
import com.example.gps_kotlin.home.Home_Page
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            navigateToFragment(Home_Page())
        }

        binding.bottomnavbarid.setOnItemSelectedListener(this)
    }

    private fun navigateToFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.commit {
            replace(R.id.frame_id, fragment)
        }
    }

    private fun homeclicked() {
        navigateToFragment(Home_Page())
    }

    private fun historyclicked() {
        navigateToFragment(HistoryFragment())
    }

    // If you need a profile fragment
    /*
    private fun profileclicked() {
        navigateToFragment(RunningFragment())
    }
    */

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bottomnav_home_id -> {
                homeclicked()
                return true
            }
            R.id.bottomnav_history_id -> {
                historyclicked()
                return true
            }

            // Uncomment and implement if you need a profile fragment
            // R.id.bottomnav_person_id -> {
            //     profileclicked()
            //     return true
            // }
            else -> return false
        }

    }

}
