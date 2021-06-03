package com.myproject.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.myproject.myapplication.network.PreferenceManager
import com.myproject.myapplication.network.WebServiceProvider


class SplashActivity : AppCompatActivity() {

    private var mContext: Context? = null
    private var arguments: Bundle? = null
    private var provider: WebServiceProvider? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        setContentView(R.layout.activity_splash)
        arguments = intent.getBundleExtra("bundle")


        Handler().postDelayed(Runnable
        // Using handler with postDelayed called runnable run method
        {
            redirectToActivity()
        }, 5 * 1000
        ) // wait for 5 seconds


    }


    fun redirectToActivity() {
        val userLoginStatus = PreferenceManager.instance(mContext).get(PreferenceManager.LOGIN_STATUS, false)
        if (userLoginStatus) {
            startActivity(Intent(mContext, HomepageActivity::class.java))
        } else
            startActivity(Intent(mContext, MainActivity::class.java))

        finish()
    }




}