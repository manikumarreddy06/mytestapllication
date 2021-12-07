package com.labhyam.app

import android.R
import android.content.Intent
import android.net.Uri
import android.net.Uri.*
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.labhyam.app.databinding.ActivityHomepageBinding
import com.labhyam.app.network.PreferenceManager


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

            if (!TextUtils.isEmpty(userType) && userType.equals(Constants.storeOwner, true)) {

            }
            else if (!TextUtils.isEmpty(userType) && userType.equals(Constants.executive, true)) {
                binding.plBtn.visibility=View.GONE
            }
            else if (!TextUtils.isEmpty(userType) && userType.equals(Constants.Helper, true)) {
                    binding.plBtn.visibility=View.GONE
                    binding.invBtn.visibility=View.GONE
            }



        }

        binding.tvUserName.text = "Welcome " + PreferenceManager.instance(this@HomepageActivity)
            .get(PreferenceManager.USER_NAME, "")

        binding.tvStoreName.text =
            PreferenceManager.instance(this@HomepageActivity).get(PreferenceManager.STORE_NAME, "")

        binding.sellBtn.setOnClickListener {
            ProductUtils.instance(this).clear()
            ProductUtils.instance(this).isOutOrderTypeFlag = true
            Intent(this, ScannerActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.buyBtn.setOnClickListener {

            ProductUtils.instance(this).clear()
            ProductUtils.instance(this).isOutOrderTypeFlag = false
            Intent(this, ScannerActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.invBtn.setOnClickListener {
            Intent(this, InventoryActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.plBtn.setOnClickListener {
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
        binding.supportcall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = parse("tel:+918861565525")
            startActivity(intent)
        }
        binding.Storeusers.setOnClickListener {
            Intent(this,storeUserActivity::class.java).also{
                startActivity(it)
            }
        }


    }
}
