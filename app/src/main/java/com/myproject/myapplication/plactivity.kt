package com.myproject.myapplication

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.myproject.myapplication.databinding.ActivityHomepageBinding
import com.myproject.myapplication.network.PreferenceManager


class plactivity: AppCompatActivity() {
    private lateinit var binding:ActivityHomepageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


}