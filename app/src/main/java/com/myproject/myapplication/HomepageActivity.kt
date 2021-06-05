package com.myproject.myapplication

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.myproject.myapplication.databinding.ActivityHomepageBinding

import com.myproject.myapplication.network.PreferenceManager


class HomepageActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomepageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.tvUserName.text="Welcome "+PreferenceManager.instance(this@HomepageActivity).get(PreferenceManager.USER_NAME,"")

        binding.tvStoreName.text=PreferenceManager.instance(this@HomepageActivity).get(PreferenceManager.STORE_NAME,"")

        binding.btn1.setOnClickListener {
            ProductUtils.instance(this).clear()
            ProductUtils.instance(this).isOutOrderTypeFlag = true
            Intent(this, ScannerActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btn2.setOnClickListener {

            ProductUtils.instance(this).clear()
            ProductUtils.instance(this).isOutOrderTypeFlag = false
                Intent(this, ScannerActivity::class.java).also {
                    startActivity(it)
                }
        }
        binding.btn3.setOnClickListener {
            Intent(this, InventoryActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.btn4.setOnClickListener {

            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
        }

            binding.btnlogout.setOnClickListener{
                val logout=AlertDialog.Builder(this)
                    .setTitle("Log out")
                    .setMessage("Are you sure you want to logout")
                    .setPositiveButton("yes") { dialogInterface: DialogInterface, i: Int ->
                        Toast.makeText(this,"logged out successfully",Toast.LENGTH_SHORT).show()

                        PreferenceManager.instance(this@HomepageActivity).clearUserSession()
                        Intent(this, MainActivity::class.java).also {
                            startActivity(it)

                        }
                        finish()
                    }
                    .setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->

                    }.create()
             logout.show()

            }
        }
}
