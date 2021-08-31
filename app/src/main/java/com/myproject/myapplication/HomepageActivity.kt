package com.myproject.myapplication

import android.R
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.myproject.myapplication.databinding.ActivityHomepageBinding
import com.myproject.myapplication.network.PreferenceManager


class HomepageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomepageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var userType =
            PreferenceManager.instance(this@HomepageActivity).get(PreferenceManager.USER_TYPE, null)
        if (!TextUtils.isEmpty(userType) && userType.equals("admin", true)) {
            binding.llStoreUser.visibility = View.GONE
            binding.llAdmin.visibility = View.VISIBLE
        } else {
            binding.llStoreUser.visibility = View.VISIBLE
            binding.llAdmin.visibility = View.GONE
        }

        binding.tvUserName.text = "Welcome " + PreferenceManager.instance(this@HomepageActivity)
            .get(PreferenceManager.USER_NAME, "")

        binding.tvStoreName.text =
            PreferenceManager.instance(this@HomepageActivity).get(PreferenceManager.STORE_NAME, "")

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
            Intent(this, ProfitActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnlogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Log Out")
                .setMessage("Are you sure you want to log out") // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(
                    R.string.yes
                ) { dialog, which ->
                    PreferenceManager.instance(this).clearUserSession()

                    Intent(this, MainActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK

                        startActivity(it)
                        finish()
                    }
                } // A null listener allows the button to dismiss the dialog and take no further action.


                .setNegativeButton(R.string.no, null)
                .setIcon(R.drawable.ic_dialog_alert)
                .show()


        }

        binding.btnStoreOnboard.setOnClickListener {
            //store on boardIn
            Intent(this, StoreCreationActivity::class.java).also {
                startActivity(it)
            }
        }


        binding.btnStoreList.setOnClickListener {

                //store list
                Intent(this,StorelistActivity::class.java).also{
                    startActivity(it)
                }
        }

    }
}
