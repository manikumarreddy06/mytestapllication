package com.myproject.myapplication.inward

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.medfin.Utils
import com.myproject.myapplication.HomepageActivity
import com.myproject.myapplication.ProductUtils
import com.myproject.myapplication.R
import com.myproject.myapplication.ScannerActivity
import com.myproject.myapplication.adapters.ProductInWardAdapters
import com.myproject.myapplication.databinding.InwardAddProductBinding
import com.myproject.myapplication.model.ProductDetailResponse
import com.myproject.myapplication.model.ProductVariant
import com.myproject.myapplication.network.PreferenceManager
import com.myproject.myapplication.network.WebServiceProvider
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class InwardProductActivity : AppCompatActivity() {



    private var productList: MutableList<ProductVariant> = ArrayList()
    private var groceryAdapter: ProductInWardAdapters? = null
    private val SECOND_ACTIVITY_REQUEST_CODE:Int=100

    var product:ProductVariant?=null


    private lateinit var binding:InwardAddProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InwardAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)


        product= intent!!.extras!!.get("addProduct") as ProductVariant
        var qty:Int =1
       binding.inwardbtn1.setOnClickListener {
           qty=binding.etProductQty.text.toString().toInt()+10
           if(qty>0) {
               binding.etProductQty.setText(qty.toString());
           }

       }

        if(product!=null){


            var tproductName:TextView= findViewById(R.id.productName)
            tproductName.text=product!!.productVariantName }


        if(ProductUtils.instance(this).isOutOrderTypeFlag){
            binding.tvHeaderTitle.text="Product Checkout"
        }
        else{
            binding.tvHeaderTitle.text="Product Addition"
        }

        var quantity:Int=1
        binding.minus.setOnClickListener(){

            quantity=binding.etProductQty.text.toString().toInt()-1
            if(quantity>0) {
                binding.etProductQty.setText(quantity.toString());
            }
            else{
                Utils.toast("quantity should be  more than zero",this)
            }
        }

        binding.plus.setOnClickListener(){
            quantity=binding.etProductQty.text.toString().toInt()+1
            binding.etProductQty.setText(quantity.toString());
        }
        var qty1: Int = 1
        binding.inwardbtn1.setOnClickListener() {
            qty = binding.etProductQty.text.toString().toInt() + 10
            binding.etProductQty.setText(qty.toString());

        }
        var qty2: Int = 1
        binding.inwardbtn2.setOnClickListener() {
            qty = binding.etProductQty.text.toString().toInt() + 25
            binding.etProductQty.setText(qty.toString());

        }
        var qty3: Int = 1
        binding.inwardbtn3.setOnClickListener() {
            qty = binding.etProductQty.text.toString().toInt() + 50
            binding.etProductQty.setText(qty.toString());

        }
        var qty4: Int = 1
        binding.inwardbtn4.setOnClickListener() {
            qty = binding.etProductQty.text.toString().toInt() + 100
            binding.etProductQty.setText(qty.toString());

        }

        binding.addMoreBtn!!.setOnClickListener(){

            if(TextUtils.isEmpty(binding.etProductQty.text.toString())){
                Utils.toast("quantity should be  more than zero",this)
            }
            else if(TextUtils.isEmpty(binding.etProcPrice.text.toString())){
                Utils.toast("procurent Price should be  more than zero",this)
            }

            else if(TextUtils.isEmpty(binding.etInputSellPrice.text.toString())){
                Utils.toast("sellPrice should be  more than zero",this)
            }

            else{


                val quantity:Int=binding.etProductQty.text.toString().toInt()
                val procuPrice:Int=binding.etProcPrice.text.toString().toInt()
                val sellPrice:Int=binding.etInputSellPrice.text.toString().toInt()
                product!!.procPrice= procuPrice.toLong()
                product!!.sellingPrice=sellPrice.toLong()
                product!!.quantity=quantity.toLong()



                ProductUtils.instance(this).addProduct(product!!)
                //add product functionlity
                Intent(this, ScannerActivity::class.java).also {
                    startActivity(it)
                }
            }





        }




        productList=ProductUtils.instance(this).productList
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

        binding.updateBtn!!.setOnClickListener(){

            if(TextUtils.isEmpty(binding.etProductQty.text.toString())){
                Utils.toast("quantity should be  more than zero",this)
            }
            else if(TextUtils.isEmpty(binding.etProcPrice.text.toString())){
                Utils.toast("procurent Price should be  more than zero",this)
            }

            else if(TextUtils.isEmpty(binding.etInputSellPrice.text.toString())){
                Utils.toast("sellPrice should be  more than zero",this)
            }

            else {


                val quantity: Int = binding.etProductQty.text.toString().toInt()
                val procuPrice: Int = binding.etProcPrice.text.toString().toInt()
                val sellPrice: Int = binding.etInputSellPrice.text.toString().toInt()
                product!!.procPrice = procuPrice.toLong()
                product!!.sellingPrice = sellPrice.toLong()
                product!!.quantity = quantity.toLong()



                ProductUtils.instance(this).addProduct(product!!)
                updateProducts(ProductUtils.instance(this).productList);
            }
        }



    }


    // This method is called when the second activity finishes
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {


                var product:ProductVariant = data!!.extras!!.get("addProduct") as ProductVariant
                productList.add(product)
                groceryAdapter!!.notifyDataSetChanged()

            }
        }


    }

    private fun updateProducts(productList: MutableList<ProductVariant>) {

        var list: MutableList<AddProduct?>? = ArrayList()

        val storeId=PreferenceManager.instance(this@InwardProductActivity).get(PreferenceManager.STORE_ID,"1").toLong()
        for (item in productList!!) {
            val ite:AddProduct= AddProduct()
            ite.variantId=item.variantId
            ite.procPrice=item.procPrice
            ite.sellingPrice=item.sellingPrice
            ite.quantity=item.quantity
            ite.storeId=storeId

            list!!.add(ite)
        }

        if(ProductUtils.instance(this).isOutOrderTypeFlag){
            Utils.showDialog(this,"Loading")
            var provider: WebServiceProvider =
                WebServiceProvider.retrofit.create(WebServiceProvider::class.java)
            provider!!.productOut(list!!)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<ProductDetailResponse> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onSuccess(response: ProductDetailResponse) {
                        Utils.hideDialog()
                        Toast.makeText(this@InwardProductActivity, "success", Toast.LENGTH_SHORT).show()

                        val intent =Intent(this@InwardProductActivity,HomepageActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish()

                    }

                    override fun onError(e: Throwable) {
                        Utils.hideDialog()
                        e.printStackTrace()
                        Toast.makeText(this@InwardProductActivity, "failure", Toast.LENGTH_SHORT).show()
                    }
                })
        }
        else{
            Utils.showDialog(this,"Loading")
            var provider: WebServiceProvider =
                WebServiceProvider.retrofit.create(WebServiceProvider::class.java)
            provider!!.productAdd(list!!)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<ProductDetailResponse> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onSuccess(response: ProductDetailResponse) {
                        Utils.hideDialog()
                        Toast.makeText(this@InwardProductActivity, "success", Toast.LENGTH_SHORT).show()

                        val intent =Intent(this@InwardProductActivity,HomepageActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish()

                    }

                    override fun onError(e: Throwable) {
                        Utils.hideDialog()
                        e.printStackTrace()
                        Toast.makeText(this@InwardProductActivity, "failure", Toast.LENGTH_SHORT).show()
                    }
                })
        }

    }
}