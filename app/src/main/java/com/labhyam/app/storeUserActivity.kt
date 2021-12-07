package com.labhyam.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.labhyam.app.databinding.ActivityHomepageBinding
import com.labhyam.app.databinding.ActivityStoreUserBinding

class storeUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoreUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}