package com.labhyam.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.labhyam.app.databinding.ActivityFabactivityBinding

class fabactivity : AppCompatActivity() {
    private lateinit var binding: ActivityFabactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFabactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.fab.setOnClickListener {
            Intent(this,ScannerActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}