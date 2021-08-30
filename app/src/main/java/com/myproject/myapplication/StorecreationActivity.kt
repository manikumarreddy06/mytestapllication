package com.myproject.myapplication

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import com.google.gson.JsonObject
import com.medfin.Utils
import com.myproject.myapplication.databinding.ActivityApprovalinventoryBinding
import com.myproject.myapplication.databinding.ActivityStorecreationBinding
import com.myproject.myapplication.network.PreferenceManager

class StorecreationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStorecreationBinding
    private var selectedPosition = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorecreationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etstoretype?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedPosition = position
            }

        }
        binding.btnadd.setOnClickListener {

            var etstorename = binding.etStorename.text.toString()

            var etcityid = binding.etcityid.text.toString()

            var etphonenumber = binding.etphonenum.text.toString()

            var etpassword = binding.etPassword1.text.toString()

            var etownername = binding.etownername.text.toString()

            if (TextUtils.isEmpty(etstorename.toString())) {
                Utils.toast("storename name can't be empty", this@StorecreationActivity)
            } else if (TextUtils.isEmpty(etcityid.toString())) {
                Utils.toast(
                    "city id cannot be empty",
                    this@StorecreationActivity
                )
            } else if (TextUtils.isEmpty(etphonenumber.toString())) {
                Utils.toast(
                    "phone number can't be empty",
                    this@StorecreationActivity
                )
            } else if (TextUtils.isEmpty(etpassword.toString())) {
                Utils.toast(
                    "password can't be empty",
                    this@StorecreationActivity
                )
            } else if (TextUtils.isEmpty(etownername.toString())) {
                Utils.toast(
                    "owner name can't be empty",
                    this@StorecreationActivity
                )
            } else if (selectedPosition == -1) {
                Utils.toast(
                    "unit type should be selected ",
                    this@StorecreationActivity
                )
            } else {

                var themes = this@StorecreationActivity.resources.getStringArray(R.array.storetype)
                var etstoretype = themes[selectedPosition]
                Utils.showDialog(this@StorecreationActivity, "Loading")
                val obj = JsonObject()
                obj.addProperty("storename", etstorename)
                obj.addProperty("cityid", etcityid)
                obj.addProperty("phonenumber", etphonenumber)
                obj.addProperty("password", etpassword)
                obj.addProperty("ownername", etownername)
                obj.addProperty("store type", etstoretype)
                obj.addProperty(
                    "storeId",
                    PreferenceManager.instance(this).get(PreferenceManager.STORE_ID, "")
                )
                Intent(this, HomepageActivity::class.java).also {
                    startActivity(it)


                }
            }

        }
    }
}