package com.myproject.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.myproject.myapplication.databinding.ProfitActivtyBinding

class plsalesactivity:AppCompatActivity() {
    private lateinit var binding: ProfitActivtyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ProfitActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}