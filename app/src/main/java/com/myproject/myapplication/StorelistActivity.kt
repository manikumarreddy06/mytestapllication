package com.myproject.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.myproject.myapplication.databinding.ActivityStorelist2Binding


class StorelistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStorelist2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStorelist2Binding.inflate(layoutInflater)
        setContentView(R.layout.activity_storelist2)
    }
}