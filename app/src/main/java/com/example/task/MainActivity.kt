package com.example.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.task.databinding.ActivityMainBinding
import com.example.task.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_holder, MainFragment())
                .commit()
        }

    }
}