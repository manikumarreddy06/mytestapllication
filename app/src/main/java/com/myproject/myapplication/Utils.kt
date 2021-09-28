package com.myproject.myapplication;

import android.Manifest
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.graphics.Outline
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.myproject.myapplication.R
import java.io.File
import java.io.IOException
import java.text.DecimalFormat
import java.util.*

class Utils {

    companion object {

        private var pDialog: ProgressDialog? = null
        private var toast: Toast? = null

        fun showDialog(context: Activity, msg: String, title: String) {
            if (pDialog == null) {
                pDialog = ProgressDialog(context)
                pDialog?.setMessage(msg)
                pDialog?.setTitle(title)
                pDialog?.setCancelable(false)
                if (pDialog?.isShowing == false && !(context as AppCompatActivity).isFinishing) {
                    pDialog?.show()
                }
            }
        }

        fun showDialog(context: Context, msg: String, title: String) {
            if (pDialog == null) {
                pDialog = ProgressDialog(context)
                pDialog?.setMessage(msg)
                pDialog?.setTitle(title)
                pDialog?.setCancelable(false)
                if (pDialog?.isShowing == false && !(context as AppCompatActivity).isFinishing) {
                    pDialog?.show()
                }
            }
        }

        fun showDialog(context: Context, msg: String) {
            if (pDialog == null) {
                pDialog = ProgressDialog(context)
                pDialog?.setMessage(msg)
                pDialog?.setTitle("")
                pDialog?.setCancelable(false)
                if (pDialog?.isShowing == false && !(context as AppCompatActivity).isFinishing) {
                    pDialog?.show()
                }
            }
        }

        fun hideDialog() {
            if (pDialog != null) {
                if (pDialog?.isShowing == true) {
                    pDialog?.dismiss()
                    pDialog = null
                }
            }
        }


        fun toast(message: String, context: Context?) {
            val v = LayoutInflater.from(context).inflate(R.layout.layout_custom_toastview, null)
            val textView = v.findViewById<TextView>(R.id.tvToast)
            textView.text = message
            toast = if (toast == null) {
                Toast(context)
            } else {
                toast?.cancel()
                Toast(context)
            }
            toast?.view = v
            toast?.duration = Toast.LENGTH_LONG
            toast?.show()
        }

    }
}