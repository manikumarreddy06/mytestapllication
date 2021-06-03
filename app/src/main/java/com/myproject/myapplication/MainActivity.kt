package com.myproject.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.widget.Toast
import com.google.gson.JsonObject
import com.myproject.myapplication.databinding.ActivityMainBinding
import com.myproject.myapplication.model.LoginResponseBean
import com.myproject.myapplication.model.User
import com.myproject.myapplication.network.PreferenceManager
import com.myproject.myapplication.network.WebServiceProvider
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

    }

    private fun doLogin(usernumber: Editable, userpassword: Editable) {
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

                    if (response.isIsvalid) {
                        onsaveData(response.user)
                    }
                    else if(!TextUtils.isEmpty(response.message)){
                        Toast.makeText(this@MainActivity, "message-->"+response.message, Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this@MainActivity, "error", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    Toast.makeText(this@MainActivity, "failure", Toast.LENGTH_SHORT).show()
                }
            })
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
        }

        //remaining fields
    }
}