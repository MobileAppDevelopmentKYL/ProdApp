package com.main.prodapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.main.prodapp.databinding.ActivityMainBinding
import com.main.prodapp.fragments.CalendarFragment
import com.main.prodapp.fragments.InboxFragment
import com.main.prodapp.fragments.ProfileFragment
import com.main.prodapp.fragments.SettingFragment
import com.main.prodapp.fragments.SignInFragment

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Start onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentSignIn = SignInFragment()
        val fragmentCalendar = CalendarFragment()
        val fragmentInbox = InboxFragment()
        val fragmentProfile = ProfileFragment()
        val fragmentSetting = SettingFragment()

        setFragment(fragmentSignIn)

        binding.bottomNavView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.calendar -> {
                    setFragment(fragmentCalendar)
                    true
                }
                R.id.inbox -> {
                    setFragment(fragmentInbox)
                    true
                }
                R.id.profile -> {
                    setFragment(fragmentProfile)
                    true
                }
                R.id.setting -> {
                    setFragment(fragmentSetting)
                    true
                }
                else -> {
                    Log.e(TAG, "Error in Navbar")
                    true
                }
            }
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

        Log.d(TAG, "Start onStop")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "Start onDestroy")
    }

    private fun setFragment(fragment : Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}

