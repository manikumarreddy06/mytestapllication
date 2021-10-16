 package com.labhyam.app.inward

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.labhyam.app.*
import com.labhyam.app.adapters.ProductInWardAdapters
import com.labhyam.app.databinding.InwardAddProductBinding
import com.labhyam.app.model.*
import com.labhyam.app.network.PreferenceManager
import com.labhyam.app.network.WebServiceProvider
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class InwardProductActivity : AppCompatActivity() {


    private var productList: MutableList<ProductVariant> = ArrayList()
    private var groceryAdapter: ProductInWardAdapters? = null
    private val SECOND_ACTIVITY_REQUEST_CODE: Int = 100

    var product: ProductVariant? = null

    var isPriceAvailble:Boolean=false


    private lateinit var binding: InwardAddProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InwardAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val storeId = PreferenceManager.instance(this).get(
            PreferenceManager.STORE_ID, "1"
        ).toString()



        product = intent!!.extras!!.get("addProduct") as ProductVariant



        if (product != null) {


            var tproductName: TextView = findViewById(R.id.productName)
            tproductName.text =
                product!!.productVariantName + "-" + product!!.getUnits() + "" + product!!.getUnitType()

        }


        binding.backButton.setOnClickListener() {
            finish()
        }
        if (ProductUtils.instance(this).isOutOrderTypeFlag) {
            binding.tvHeaderTitle.text = "Product Checkout"
            getProductPrice(product!!.variantId)
            binding.llPurchaseContainer.visibility= View.GONE
        } else {
            binding.tvHeaderTitle.text = "Product Addition"
            binding.llPurchaseContainer.visibility= View.VISIBLE
        }

        var quantity: Float = 1.0f
        binding.minus.setOnClickListener() {

            quantity = binding.etProductQty.text.toString().toFloat() - 1
            if (quantity > 0) {
                binding.etProductQty.setText(quantity.toString());
            } else {
                Utils.toast("quantity should be  more than zero", this)
            }
        }

        binding.plus.setOnClickListener() {
            quantity = binding.etProductQty.text.toString().toFloat() + 1
            binding.etProductQty.setText(quantity.toString());
        }


        var qty: Float = 1.0f
        binding.inwardbtn1.setOnClickListener() {
            qty = binding.etProductQty.text.toString().toFloat() + 10
            binding.etProductQty.setText(qty.toString());

        }
        binding.inwardbtn2.setOnClickListener() {
            qty = binding.etProductQty.text.toString().toFloat() + 25
            binding.etProductQty.setText(qty.toString());

        }
        binding.inwardbtn3.setOnClickListener() {
            qty = binding.etProductQty.text.toString().toFloat() + 50
            binding.etProductQty.setText(qty.toString());

        }
        binding.inwardbtn4.setOnClickListener() {
            qty = binding.etProductQty.text.toString().toFloat() + 100
            binding.etProductQty.setText(qty.toString());

        }


        binding.addMoreBtn!!.setOnClickListener() {

            if(ProductUtils.instance(this).isOutOrderTypeFlag) {
                if (TextUtils.isEmpty(binding.etProductQty.text.toString())) {
                    Utils.toast("quantity should be  more than zero", this)
                }else if (!isPriceAvailble && TextUtils.isEmpty(binding.etProcPrice.text.toString().toString())) {
                    Utils.toast("procurment should be  more than zero", this)
                }
                else if (TextUtils.isEmpty(binding.etInputSellPrice.text.toString().toString())) {
                    Utils.toast("sellPrice should be  more than zero", this)
                } else {



                    var procuPrice:Float= 0F
                    val quantity: Float = binding.etProductQty.text.toString().toFloat()
                    if(!TextUtils.isEmpty(binding.etProcPrice.text.toString().toFloat().toString())){

                        procuPrice = binding.etProcPrice.text.toString().toFloat()
                    }
                    val sellPrice: Float = binding.etInputSellPrice.text.toString().toFloat()
                    product!!.procPrice = procuPrice.toFloat()//changed
                    product!!.sellingPrice = sellPrice.toFloat()//changed
                    product!!.quantity = quantity.toFloat()



                    ProductUtils.instance(this).addProduct(product!!)
                    //add product functionlity
                    Intent(this, ScannerActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }

                }
            }
            else{

                if (TextUtils.isEmpty(binding.etProductQty.text.toString())) {
                    Utils.toast("quantity should be  more than zero", this)
                } else if (TextUtils.isEmpty(binding.etProcPrice.text.toString())) {
                    Utils.toast("procurment Price should be  more than zero", this)
                } else if (TextUtils.isEmpty(binding.etInputSellPrice.text.toString())) {
                    Utils.toast("sellPrice should be  more than zero", this)
                } else {



                    var procuPrice:Float= 0F
                    val quantity: Float = binding.etProductQty.text.toString().toFloat()
                    if(!TextUtils.isEmpty(binding.etProcPrice.text.toString().toFloat().toString())){

                        procuPrice = binding.etProcPrice.text.toString().toFloat()
                    }
                    val sellPrice: Float = binding.etInputSellPrice.text.toString().toFloat()
                    product!!.procPrice = procuPrice.toFloat()//changed
                    product!!.sellingPrice = sellPrice.toFloat()//changed
                    product!!.quantity = quantity.toFloat()



                    ProductUtils.instance(this).addProduct(product!!)
                    //add product functionlity
                    Intent(this, ScannerActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }

                }
            }


        }




        productList = ProductUtils.instance(this).productList
        groceryAdapter = ProductInWardAdapters(productList, applicationContext)
        val horizontalLayoutManager =
            LinearLayoutManager(this@InwardProductActivity, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.setLayoutManager(horizontalLayoutManager)
        binding.rvContent.setAdapter(groceryAdapter)



        binding.rvContent.addItemDecoration(
            DividerItemDecoration(
                this@InwardProductActivity,
                LinearLayoutManager.VERTICAL
            )
        )

        binding.updateBtn!!.setOnClickListener() {


            if(ProductUtils.instance(this).isOutOrderTypeFlag) {
                if (TextUtils.isEmpty(binding.etProductQty.text.toString())) {
                    Utils.toast("quantity should be  more than zero", this)
                } else if (!isPriceAvailble && TextUtils.isEmpty(binding.etProcPrice.text.toString())) {//
                    Utils.toast("procurment should be  more than zero", this)
                }
                else if (TextUtils.isEmpty(binding.etInputSellPrice.text.toString())) {//
                    Utils.toast("sellPrice should be  more than zero", this)
                }
                else {


                    var procuPrice:Float=0F
                    val quantity: Float = binding.etProductQty.text.toString().toFloat()
                    if(!TextUtils.isEmpty(binding.etProcPrice.text.toString().toFloat().toString())){//changed
                        procuPrice = binding.etProcPrice.text.toString().toFloat()//changed
                    }
                    val sellPrice: Float = binding.etInputSellPrice.text.toString().toFloat()//changed
                    product!!.procPrice = procuPrice.toFloat()//changed
                    product!!.sellingPrice = sellPrice.toFloat()//changed
                    product!!.quantity = quantity.toFloat()



                    ProductUtils.instance(this).addProduct(product!!)

                    Intent(this, CheckoutActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                    //updateProducts(ProductUtils.instance(this).productList);
                }
            }
            else{
                if (TextUtils.isEmpty(binding.etProductQty.text.toString())) {
                    Utils.toast("quantity should be  more than zero", this)
                } else if (TextUtils.isEmpty(binding.etProcPrice.text.toString().toFloat().toString())) {
                    Utils.toast("procurment Price should be  more than zero", this)
                } else if (TextUtils.isEmpty(binding.etInputSellPrice.text.toString().toFloat().toString())) {
                    Utils.toast("sellPrice should be  more than zero", this)
                } else {



                    var procuPrice:Float=0F
                    val quantity: Float = binding.etProductQty.text.toString().toFloat()
                    if(!TextUtils.isEmpty(binding.etProcPrice.text.toString().toFloat().toString())){

                        procuPrice = binding.etProcPrice.text.toString().toFloat()
                    }
                    val sellPrice: Float = binding.etInputSellPrice.text.toString().toFloat()
                    product!!.procPrice = procuPrice.toFloat()//changed
                    product!!.sellingPrice = sellPrice.toFloat()//changed
                    product!!.quantity = quantity.toFloat()



                    ProductUtils.instance(this).addProduct(product!!)

                    Intent(this, CheckoutActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                    //updateProducts(ProductUtils.instance(this).productList);
                }
            }
        }


    }


    // This method is called when the second activity finishes
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {


                var product: ProductVariant = data!!.extras!!.get("addProduct") as ProductVariant
                productList.add(product)
                groceryAdapter!!.notifyDataSetChanged()

            }
        }


    }

    private fun getProductPrice(variantId:Long) {

        val storeId=PreferenceManager.instance(this).get(PreferenceManager.STORE_ID,"1").toString()

        val obj = JsonObject()
        obj.addProperty("storeId", storeId)

        obj.addProperty("variantId", variantId)
        Utils.showDialog(this,"Loading")
        var provider: WebServiceProvider =
            WebServiceProvider.retrofit.create(WebServiceProvider::class.java)
        provider.getProductPrice(obj)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ProductPriceResponseBean> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: ProductPriceResponseBean) {
                    Utils.hideDialog()
                    if(response.isIsvalid()) {
                        updateSellingPrice(response)
                    }
                    else{
                        updateSellingPrice(response)
                        Toast.makeText(this@InwardProductActivity, "product not found", Toast.LENGTH_SHORT).show()

                    }

                }

                override fun onError(e: Throwable) {
                    Utils.hideDialog()
                    e.printStackTrace()
                    Toast.makeText(this@InwardProductActivity, "failure", Toast.LENGTH_SHORT).show()
                }
            })

    }

    private fun updateSellingPrice(response: ProductPriceResponseBean) {
        if(!TextUtils.isEmpty(response.sellingPrice)) {
            binding.etInputSellPrice.setText(response.sellingPrice)
            binding.etProcPrice.setText(response.procurementPrice)
            isPriceAvailble=true
        }
        else{
            binding.llPurchaseContainer.visibility= View.VISIBLE
        }
    }



}


