package com.labhyam.app

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import com.google.gson.JsonObject
import com.labhyam.app.databinding.ActivityMainBinding
import com.labhyam.app.model.LoginResponseBean
import com.labhyam.app.model.User
import com.labhyam.app.network.PreferenceManager
import com.labhyam.app.network.WebServiceProvider
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSubmit.setOnClickListener {


            var username = binding.etMobilenum.text
            var userpassword = binding.etPassword.text

            doLogin(username, userpassword)


        }

        binding.btnuseronboard.setOnClickListener {
            Intent(this,UseronboardActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
        binding.supportcall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+918861565525")
            startActivity(intent)
        }


        }

    private fun doLogin(usernumber: Editable, userpassword: Editable) {

        var mobileNumber = binding.etMobilenum.text.toString()


        var password = binding.etPassword.text.toString()

        if(TextUtils.isEmpty(mobileNumber)){
            Utils.toast("mobile number can't be empty",this@MainActivity)
        }
        else if(TextUtils.isEmpty(password)){
            Utils.toast("password can't be empty",this@MainActivity)
        }
        else {


            Utils.showDialog(this, "loading",)
            val obj = JsonObject()
            obj.addProperty("userPhoneNumber", usernumber.toString())
            obj.addProperty("password", userpassword.toString())


            var provider: WebServiceProvider =
                WebServiceProvider.retrofit.create(WebServiceProvider::class.java)
            provider.login(obj)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<LoginResponseBean> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onSuccess(response: LoginResponseBean) {
                        Utils.hideDialog()
                        if (response.isIsvalid) {
                            onsaveData(response.user)
                        } else if (!TextUtils.isEmpty(response.message)) {
                            Utils.toast(response.message, this@MainActivity)
                        } else {
                            Utils.toast("error ", this@MainActivity)
                        }
                    }

                    override fun onError(e: Throwable) {
                        Utils.hideDialog()
                        e.printStackTrace()
                        Utils.toast("error ", this@MainActivity)
                    }
                })
        }
    }

    private fun onsaveData(response: User) {

        var preferenceManager:PreferenceManager= PreferenceManager.instance(this,)

        preferenceManager.set(PreferenceManager.USER_NAME,response.username)
        preferenceManager.set(PreferenceManager.LOGIN_STATUS,true)
        preferenceManager.set(PreferenceManager.USER_ID,response.id)
        preferenceManager.set(PreferenceManager.USER_MOBILE_NUMBER,response.userPhoneNumber)
        preferenceManager.set(PreferenceManager.CITY_ID,response.cityId)
        preferenceManager.set(PreferenceManager.STORE_ID,response.storeId)
        preferenceManager.set(PreferenceManager.STORE_NAME,response.storeName)

        preferenceManager.set(PreferenceManager.USER_TYPE,response.userTypeName)




        Intent(this,HomepageActivity::class.java).also {
            startActivity(it)
            finish()
        }


      }

    }
