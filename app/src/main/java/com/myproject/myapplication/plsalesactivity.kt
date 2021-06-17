package com.myproject.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.myproject.myapplication.databinding.PlslaesBinding

class plsalesactivity:AppCompatActivity() {
    private lateinit var binding: PlslaesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=PlslaesBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}