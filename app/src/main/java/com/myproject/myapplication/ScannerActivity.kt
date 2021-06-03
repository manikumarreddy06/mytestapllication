package com.myproject.myapplication

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.media.MediaBrowserServiceCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.google.zxing.Result
import com.medfin.Utils
import com.myproject.myapplication.ZXingScannerView.ResultHandler
import com.myproject.myapplication.adapters.ProductListAdapter
import com.myproject.myapplication.model.*
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
        doProductSearch()


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

    private fun doProductSearch(productCode: String) {
        val obj = JsonObject()
        obj.addProperty("productCode", productCode)


        var provider: WebServiceProvider =
            WebServiceProvider.retrofit.create(WebServiceProvider::class.java)
        provider.productSearch(obj)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ProductDetailResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: ProductDetailResponse) {
                   
                    if(response.isIsvalid()) {

                        Toast.makeText(this@ScannerActivity, "suceess", Toast.LENGTH_SHORT).show()

                        showpopup(response!!.productDetails!!.productVariants,response!!.productDetails!!)
                    }
                    else{
                        Toast.makeText(this@ScannerActivity, "product not found", Toast.LENGTH_SHORT).show()

                    }

                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    Toast.makeText(this@ScannerActivity, "failure", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun doProductSearch() {



        var provider: WebServiceProvider =
            WebServiceProvider.retrofit.create(WebServiceProvider::class.java)
        provider.productSearchbyCategory()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<CategoryResponseBean> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: CategoryResponseBean) {

                    if(response.isIsvalid()) {

                        Toast.makeText(this@ScannerActivity, "suceess", Toast.LENGTH_SHORT).show()
                        
                        prepareCategoryUI(response.categoryItems)
                    }
                    else{
                        Toast.makeText(this@ScannerActivity, "product not found", Toast.LENGTH_SHORT).show()

                    }

                }

                override fun onError(e: Throwable) {
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


            groceryRecyclerView = view.findViewById(R.id.rvcontent)
            // add a divider after each item for more clarity
            groceryRecyclerView!!.addItemDecoration(
                DividerItemDecoration(
                    this@ScannerActivity,
                    LinearLayoutManager.HORIZONTAL
                )
            )
            groceryAdapter = ProductListAdapter(item.value, applicationContext,object : ProductListAdapter.RecyclerViewClickListener {
                override fun onClick(product: ProductVariant?) {

                }
            });

            val horizontalLayoutManager =LinearLayoutManager(this@ScannerActivity, LinearLayoutManager.HORIZONTAL, false)
            groceryRecyclerView!!.setLayoutManager(horizontalLayoutManager)
            groceryRecyclerView!!.setAdapter(groceryAdapter)
            populategroceryList()

            viewGroup.addView(view)
        }
    }


    private fun showpopup(productVariants: MutableList<ProductVariant>, productDetails: ProductDetails) {


        var selectedPosition:Int=-1;
        val dialog = Dialog(this@ScannerActivity)
        // Include dialog.xml file
        // Include dialog.xml file
        dialog.setContentView(R.layout.complete_dialog_filter)
        // Set dialog title
        // Set dialog title


        val spinner: Spinner =dialog.findViewById(R.id.spVariant)


        val btnSubmit: Button =dialog.findViewById(R.id.btnSubmit)

        val btnCancel: Button =dialog.findViewById(R.id.btnCancel)


        val etProductQty: EditText =dialog.findViewById(R.id.etProductQty)
        val etProcPrice: EditText =dialog.findViewById(R.id.etProcPrice)
        val etInputSellPrice: EditText =dialog.findViewById(R.id.etInputSellPrice)

        dialog.show();

        val outlet: MutableList<String> = ArrayList()
        outlet.add("Select")
        for (item in productVariants)
            outlet.add(item.productVariantName)

        // Initializing an ArrayAdapter
        val adapter = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_dropdown_item, // Layout
            outlet // Array
        )

        // Set the drop down view resource
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        // Finally, data bind the spinner object with dapter
        spinner.adapter = adapter;

        // Set an on item selected listener for spinner object
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view
                //text_view.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                selectedPosition=position
            }

            override fun onNothingSelected(parent: AdapterView<*>){




            }
        }


        btnSubmit.setOnClickListener{







            if(selectedPosition<0){
                Utils.toast("select variant",this@ScannerActivity)
            }
            else if(TextUtils.isEmpty(etProductQty.text.toString())){
                Utils.toast("quantity should be  more than zero",this@ScannerActivity)
            }
            else if(TextUtils.isEmpty(etProcPrice.text.toString())){
                Utils.toast("procurent Price should be  more than zero",this@ScannerActivity)
            }

            else if(TextUtils.isEmpty(etInputSellPrice.text.toString())){
                Utils.toast("sellPrice should be  more than zero",this@ScannerActivity)
            }

            else{


                val quantity:Int=etProductQty.text.toString().toInt()
                val procuPrice:Int=etProcPrice.text.toString().toInt()
                val sellPrice:Int=etInputSellPrice.text.toString().toInt()
                var prodVariant:ProductVariant= productVariants[selectedPosition-1]
                prodVariant.procPrice= procuPrice.toLong()
                prodVariant.sellingPrice=sellPrice.toLong()
                prodVariant.quantity=quantity.toLong()


                val intent = Intent()
                intent.putExtra("addProduct", prodVariant)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }


        }

        btnCancel.setOnClickListener{


            Toast.makeText(this@ScannerActivity, "product cancel ", Toast.LENGTH_SHORT).show()
        }








    }



    private fun showpopupWithoutBarcode(product: ProductDetails?) {


        var selectedPosition:Int=-1;
        val dialog = Dialog(this@ScannerActivity)
        // Include dialog.xml file
        // Include dialog.xml file
        dialog.setContentView(R.layout.complete_dialog_filter)
        // Set dialog title
        // Set dialog title


        val spinner: Spinner =dialog.findViewById(R.id.spVariant)


        val containerSupervisor: LinearLayout =dialog.findViewById(R.id.containerSupervisor)


        containerSupervisor.visibility=View.GONE


        val btnSubmit: Button =dialog.findViewById(R.id.btnSubmit)

        val btnCancel: Button =dialog.findViewById(R.id.btnCancel)


        val etProductQty: EditText =dialog.findViewById(R.id.etProductQty)
        val etProcPrice: EditText =dialog.findViewById(R.id.etProcPrice)
        val etInputSellPrice: EditText =dialog.findViewById(R.id.etInputSellPrice)

        dialog.show();



        btnSubmit.setOnClickListener{

           if(TextUtils.isEmpty(etProductQty.text.toString())){
                Utils.toast("quantity should be  more than zero",this@ScannerActivity)
            }
            else if(TextUtils.isEmpty(etProcPrice.text.toString())){
                Utils.toast("procurent Price should be  more than zero",this@ScannerActivity)
            }

            else if(TextUtils.isEmpty(etInputSellPrice.text.toString())){
                Utils.toast("sellPrice should be  more than zero",this@ScannerActivity)
            }

            else{


               val quantity:Int=etProductQty.text.toString().toInt()
               val procuPrice:Int=etProcPrice.text.toString().toInt()
               val sellPrice:Int=etInputSellPrice.text.toString().toInt()
               var prodVariant:ProductVariant=ProductVariant();
                prodVariant.procPrice= procuPrice.toLong()
                prodVariant.sellingPrice=sellPrice.toLong()
                prodVariant.quantity=quantity.toLong()
               prodVariant.variantId=1;
               prodVariant.productVariantName=product!!.productName;


                val intent = Intent()
                intent.putExtra("addProduct", prodVariant)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }


        }

        btnCancel.setOnClickListener{


            Toast.makeText(this@ScannerActivity, "product cancel ", Toast.LENGTH_SHORT).show()
        }








    }

}