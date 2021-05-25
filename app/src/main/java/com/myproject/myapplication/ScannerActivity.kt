package com.myproject.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.media.MediaBrowserServiceCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.google.zxing.Result
import com.myproject.myapplication.ZXingScannerView.ResultHandler
import com.myproject.myapplication.adapters.ProductListAdapter
import com.myproject.myapplication.model.LoginResponseBean
import com.myproject.myapplication.model.ProductDetails
import com.myproject.myapplication.network.WebServiceProvider
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.*

class ScannerActivity : AppCompatActivity(), ResultHandler {
    private var mScannerView: ZXingScannerView? = null
    var CAMERA = 0
    var position: String? = null
    var formt: String? = null
    private val productList: MutableList<ProductDetails> = ArrayList()
    private var groceryRecyclerView: RecyclerView? = null
    private var groceryAdapter: ProductListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(this));
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 5)
            }
        }
        val contentFrame = findViewById<View>(R.id.content_frame) as ViewGroup
        mScannerView = ZXingScannerView(this)
        contentFrame.addView(mScannerView)
        groceryRecyclerView = findViewById(R.id.rvcontent)
        // add a divider after each item for more clarity
        groceryRecyclerView!!.addItemDecoration(
            DividerItemDecoration(
                this@ScannerActivity,
                LinearLayoutManager.HORIZONTAL
            )
        )
        groceryAdapter = ProductListAdapter(productList, applicationContext)
        val horizontalLayoutManager =
            LinearLayoutManager(this@ScannerActivity, LinearLayoutManager.HORIZONTAL, false)
        groceryRecyclerView!!.setLayoutManager(horizontalLayoutManager)
        groceryRecyclerView!!.setAdapter(groceryAdapter)
        populategroceryList()
    }

    private fun populategroceryList() {
        val potato = ProductDetails("Potato")
        val onion = ProductDetails("Onion")
        val cabbage = ProductDetails("Cabbage")
        val cauliflower = ProductDetails("Cauliflower")
        productList.add(potato)
        productList.add(onion)
        productList.add(cabbage)
        productList.add(cauliflower)
        groceryAdapter!!.notifyDataSetChanged()
    }

    public override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this)
        mScannerView!!.startCamera()
    }

    public override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()
    }

    override fun handleResult(rawResult: Result) {
        Toast.makeText(
            this,
            "Contents = " + rawResult.text + ", Format = " + rawResult.barcodeFormat.toString(),
            Toast.LENGTH_LONG
        ).show()
        position = rawResult.text
        formt = rawResult.barcodeFormat.toString()
        val intent = Intent()
        intent.putExtra("Contents", position)
        intent.putExtra("Format", formt)
        setResult(RESULT_OK, intent)
        doProductSearch(rawResult.text)
    }

    override fun handleResult(rawResult: MediaBrowserServiceCompat.Result<*>?) {}

    companion object {
        private const val WRITE_EXST = 1
        private const val REQUEST_PERMISSION = 123
    }

    private fun doProductSearch(productCode:String) {
        val obj = JsonObject()
        obj.addProperty("productCode", productCode)


        var provider: WebServiceProvider =
            WebServiceProvider.retrofit.create(WebServiceProvider::class.java)
        provider.productSearch(obj)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<LoginResponseBean> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: LoginResponseBean) {
                    Toast.makeText(this@ScannerActivity, "suceess", Toast.LENGTH_SHORT).show()


                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    Toast.makeText(this@ScannerActivity, "failure", Toast.LENGTH_SHORT).show()
                }
            })
    }
}