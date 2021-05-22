package com.myproject.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.inflate
import com.myproject.myapplication.databinding.ActivityHomepageBinding
import com.myproject.myapplication.databinding.ActivityHomepageBinding.inflate
import com.myproject.myapplication.databinding.ActivityMainBinding
import com.myproject.myapplication.databinding.ActivityMainBinding.inflate

class homepageActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomepageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btn1.setOnClickListener {
           Intent(this,ScannerActivity::class.java).also {
               startActivity(it)
           }
            binding.btn2.setOnClickListener {
                Intent(this,inactivity::class.java).also {
                    startActivity(it)
                }
            }
            binding.btn3.setOnClickListener {
             Intent(this,outactivity::class.java).also {
                 startActivity(it)
             }
            }
            binding.btn4.setOnClickListener {
             Intent(this,plactivity::class.java).also {
                 startActivity(it)
             }
            }



        }
    }
}
