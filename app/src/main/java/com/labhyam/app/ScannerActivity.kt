package com.labhyam.app

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.media.MediaBrowserServiceCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.google.zxing.Result
import com.labhyam.app.ZXingScannerView.ResultHandler
import com.labhyam.app.adapters.ProductListAdapter
import com.labhyam.app.adapters.SearchListAdapter
import com.labhyam.app.inward.InwardProductActivity
import com.labhyam.app.model.*
import com.labhyam.app.network.PreferenceManager
import com.labhyam.app.network.WebServiceProvider
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
    var autoCompleteTextView:AutoCompleteTextView??=null



    private var searchRecyclerView: RecyclerView? = null
    var searchList:List<ProductVariant> = ArrayList()
    var searchListAdapter: SearchListAdapter?=null
    var clearIcon:ImageView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
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

        if (ProductUtils.getCategoryList() != null && ProductUtils.getCategoryList().size > 0) {
            prepareCategoryUI(ProductUtils.getCategoryList())
        } else {
            doProductSearch()
        }

        var tvHeaderTitle: TextView = findViewById(R.id.tvHeaderTitle)
        if (ProductUtils.instance(this).isOutOrderTypeFlag) {
            tvHeaderTitle.text = "Add New Product"
        } else {
            tvHeaderTitle.text = "Product Addition"
        }

        val btnaddproduct: Button = findViewById(R.id.addproduct)
        btnaddproduct.setOnClickListener {
            Intent(this,ProductAdditionActivity::class.java).also {
                startActivity(it)
            }
        }

        val backButton: ImageView = findViewById(R.id.backButton)

        backButton.setOnClickListener(){
            finish()
        }






        searchListAdapter = SearchListAdapter(searchList!!, applicationContext,object : SearchListAdapter.RecyclerViewClickListener {
            override fun onClick(product: ProductVariant?) {
                searchRecyclerView!!.visibility=View.GONE
                autoCompleteTextView!!.setText("")
                val intent =Intent(this@ScannerActivity,InwardProductActivity::class.java)
                intent.putExtra("addProduct", product)
                startActivity(intent)


            }
        });
        //Getting the instance of AutoCompleteTextView
        //Getting the instance of AutoCompleteTextView
        clearIcon= findViewById<View>(R.id.imClearIcon) as ImageView
        searchRecyclerView = findViewById<View>(R.id.rvContent) as RecyclerView
        autoCompleteTextView = findViewById<View>(R.id.tvSearch) as AutoCompleteTextView
        autoCompleteTextView!!.threshold = 1 //will start working from first character
         //setting the adapter data into the AutoCompleteTextView

        autoCompleteTextView!!.setTextColor(Color.RED)

        val horizontalLayoutManager =LinearLayoutManager(this@ScannerActivity, LinearLayoutManager.VERTICAL, false)
        searchRecyclerView!!.setLayoutManager(horizontalLayoutManager)
        searchRecyclerView!!.setAdapter(searchListAdapter)
        searchRecyclerView!!.addItemDecoration(
            DividerItemDecoration(
                this@ScannerActivity,
                LinearLayoutManager.VERTICAL
            )
        )

        autoCompleteTextView!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.d("onTextChanged",s.toString()+"--->"+s.length)
                if(s.length>2){
                    doProductSearchbyName(s.toString())
                }
            }
        })
        clearIcon!!.setOnClickListener(){
            searchRecyclerView!!.visibility=View.GONE
            autoCompleteTextView!!.setText("")
        }

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
//        Toast.makeText(
//            this,
//            "Contents = " + rawResult.text + ", Format = " + rawResult.barcodeFormat.toString(),
//            Toast.LENGTH_LONG
//        ).show()
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

    private fun doProductSearch(productCode: String) {
        Utils.showDialog(this@ScannerActivity,"Loading")
        val obj = JsonObject()
        obj.addProperty("productCode", productCode)


        var provider: WebServiceProvider =
            WebServiceProvider.retrofit.create(WebServiceProvider::class.java).also {
                it.productSearch(obj)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : SingleObserver<ProductDetailResponse> {
                        override fun onSubscribe(d: Disposable) {

                        }

                        override fun onSuccess(response: ProductDetailResponse) {
                            Utils.hideDialog()
                            if (response.isIsvalid()) {

                                //Toast.makeText(this@ScannerActivity, "success", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@ScannerActivity, InwardProductActivity::class.java)
                                intent.putExtra("addProduct", response.productVariants[0])
                                startActivity(intent)
                            }
                           else {

                                AlertDialog.Builder(this@ScannerActivity)
                                    .setTitle("Info")
                                    .setMessage("Product is not found") // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton(
                                        android.R.string.yes
                                    ) { dialog, which ->
                                        mScannerView!!.setResultHandler(this@ScannerActivity)
                                        mScannerView!!.startCamera()
                                    }

                                    .setNegativeButton(android.R.string.no, null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                     .show()

                            }

                        }

                        override fun onError(e: Throwable) {
                            Utils.hideDialog()
                            e.printStackTrace()
                            Toast.makeText(this@ScannerActivity, "failure", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
     }

    private fun doProductSearch() {
        val storeId= PreferenceManager.instance(this).get(
            PreferenceManager.STORE_ID,"1").toString()
        Utils.showDialog(this@ScannerActivity,"Loading")
        var provider: WebServiceProvider =
            WebServiceProvider.retrofit.create(WebServiceProvider::class.java)
        provider.productSearchbyCategory(storeId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<CategoryResponseBean> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: CategoryResponseBean) {
                    Utils.hideDialog()
                    if(response.isIsvalid()) {

                        prepareCategoryUI(response.categoryItems)
                        ProductUtils.setCategoryList(response.categoryItems)
                    }
                    else if( !response.isIsvalid() && !TextUtils.isEmpty(response.message)) {
                        Toast.makeText(this@ScannerActivity, response.message, Toast.LENGTH_SHORT).show()
                    }
                    else {
                            Toast.makeText(this@ScannerActivity, "No Product Found", Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onError(e: Throwable) {
                    Utils.hideDialog()
                    e.printStackTrace()
                    Toast.makeText(this@ScannerActivity, "failure", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun prepareCategoryUI(categoryItems: List<CategoryItem>) {

        val inflater: LayoutInflater = this@ScannerActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewGroup : ViewGroup = findViewById (R.id.container)
        viewGroup!!.removeAllViews()
        for (item in categoryItems) {

            val view : ViewGroup = inflater.inflate(R.layout.other_cat_item, viewGroup, false) as ViewGroup


            var t:TextView = view.findViewById(R.id.tvTitle)
            t.text=item.key

            groceryRecyclerView = view.findViewById(R.id.rvcontent)
            // add a divider after each item for more clarity
//            groceryRecyclerView!!.addItemDecoration(
//                DividerItemDecoration(
//                    this@ScannerActivity,
//                    LinearLayoutManager.HORIZONTAL
//                )
//            )
            groceryAdapter = ProductListAdapter(item.value, applicationContext,object : ProductListAdapter.RecyclerViewClickListener {
                override fun onClick(product: ProductVariant?) {


                    val intent =Intent(this@ScannerActivity,InwardProductActivity::class.java)
                    intent.putExtra("addProduct", product)
                    startActivity(intent)

                    
                }
            });

            val horizontalLayoutManager =LinearLayoutManager(this@ScannerActivity, LinearLayoutManager.HORIZONTAL, false)
            groceryRecyclerView!!.setLayoutManager(horizontalLayoutManager)
            groceryRecyclerView!!.setAdapter(groceryAdapter)


            viewGroup.addView(view)
        }
    }




    private fun doProductSearchbyName(productCode: String) {
        Utils.showDialog(this@ScannerActivity,"Loading")
        val obj = JsonObject()
        obj.addProperty("searchString", productCode)


        var provider: WebServiceProvider =
            WebServiceProvider.retrofit.create(WebServiceProvider::class.java).also {
                it.productSearchbyName(obj)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : SingleObserver<ProductDetailResponse> {
                        override fun onSubscribe(d: Disposable) {

                        }

                        override fun onSuccess(response: ProductDetailResponse) {
                            Utils.hideDialog()
                            if (response.isIsvalid()) {

                                  updateAutoCompleteAdapters(response.productVariants)
//                                val intent = Intent(this@ScannerActivity, InwardProductActivity::class.java)
//                                intent.putExtra("addProduct", response.productVariants[0])
//                                startActivity(intent)
                            }
                            else {
                                updateEmptyAdapters()
                                AlertDialog.Builder(this@ScannerActivity)
                                    .setTitle("Info")
                                    .setMessage("Product is not found") // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton(
                                        android.R.string.yes
                                    ) { dialog, which ->
                                        mScannerView!!.setResultHandler(this@ScannerActivity)
                                        mScannerView!!.startCamera()
                                    }

                                    .setNegativeButton(android.R.string.no, null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show()

                            }

                        }

                        override fun onError(e: Throwable) {
                            Utils.hideDialog()
                            e.printStackTrace()
                            Toast.makeText(this@ScannerActivity, "failure", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
    }

    private fun updateAutoCompleteAdapters(productVariants: List<ProductVariant>) {

        if(productVariants!=null && productVariants!!.size>0){
            searchListAdapter!!.setData(productVariants!!)
            searchRecyclerView!!.visibility=View.VISIBLE
        }
        else {
            searchList.isEmpty()
            searchListAdapter!!.setData(searchList )
            searchRecyclerView!!.visibility=View.GONE
        }

    }

    private fun updateEmptyAdapters() {

        searchList.isEmpty()
        searchListAdapter!!.setData(searchList )
        searchRecyclerView!!.visibility=View.GONE
    }


}














