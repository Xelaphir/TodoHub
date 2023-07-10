package com.example.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.task.databinding.ActivityMainBinding
import com.example.task.ui.main.MainFragment
import com.example.task.ui.navigator.Navigator

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        if(savedInstanceState == null) {
            launchFragment(MainFragment(), false)
        }

    }

    private fun launchFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(null)
        transaction
            .replace(R.id.fragment_holder, fragment)
            .commit()
    }

    override fun launch(fragment: Fragment) {
        launchFragment(fragment)
    }

    @Suppress("DEPRECATION")
    override fun goBack() {
        onBackPressed()
    }
}