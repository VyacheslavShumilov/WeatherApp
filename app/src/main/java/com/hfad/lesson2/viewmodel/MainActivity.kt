package com.hfad.lesson2.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hfad.lesson2.R
import com.hfad.lesson2.databinding.ActivityMainBinding

import com.hfad.lesson2.view.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.ok.setOnClickListener(clickListener)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commit()
        }
    }
}
