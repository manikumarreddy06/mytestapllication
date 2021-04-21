package com.myproject.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.myproject.myapplication.databinding.ActivityHomepageBinding
import com.myproject.myapplication.databinding.ActivityStoreactivityBinding

class storeactivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoreactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}