package com.myproject.myapplication

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.myproject.myapplication.databinding.ActivityHomepageBinding
import com.myproject.myapplication.inward.InwardProductActivity

import com.myproject.myapplication.network.PreferenceManager


class homepageActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomepageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.tvUserName.text="Name : "+PreferenceManager.instance(this@homepageActivity).get(PreferenceManager.USER_NAME,"")

        binding.tvStoreName.text="Store Name : "+PreferenceManager.instance(this@homepageActivity).get(PreferenceManager.STORE_NAME,"")

        binding.tvUserType.text="Type : "+PreferenceManager.instance(this@homepageActivity).get(PreferenceManager.USER_TYPE,"")
        binding.btn1.setOnClickListener {
            Intent(this, StoreInvactivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btn2.setOnClickListener {
                Intent(this, ScannerActivity::class.java).also {
                    startActivity(it)
                }
        }
        binding.btn3.setOnClickListener {
             Intent(this, outactivity::class.java).also {
                 startActivity(it)
             }
        }
        binding.btn4.setOnClickListener {

        }

            binding.btnLogout.setOnClickListener{
                val logout=AlertDialog.Builder(this)
                    .setTitle("Log out")
                    .setMessage("Are you sure you want to logout")
                    .setPositiveButton("yes") { dialogInterface: DialogInterface, i: Int ->
                        Toast.makeText(this,"logged out successfully",Toast.LENGTH_SHORT).show()

                        PreferenceManager.instance(this@homepageActivity).clearUserSession()
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
