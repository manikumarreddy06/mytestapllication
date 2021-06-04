package com.myproject.myapplication.inward

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.myproject.myapplication.R
import com.myproject.myapplication.ScannerActivity
import com.myproject.myapplication.adapters.ProductInWardAdapters
import com.myproject.myapplication.model.ProductDetailResponse
import com.myproject.myapplication.model.ProductVariant
import com.myproject.myapplication.network.PreferenceManager
import com.myproject.myapplication.network.WebServiceProvider
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class InwardProductActivity : AppCompatActivity() {



    private val productList: MutableList<ProductVariant> = ArrayList()
    private var groceryRecyclerView: RecyclerView? = null
    private var groceryAdapter: ProductInWardAdapters? = null
    private val SECOND_ACTIVITY_REQUEST_CODE:Int=100
    private var updateBtn: Button?=null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inward)

        groceryRecyclerView = findViewById(R.id.rvContent)

//         var tvAddItem: TextView = findViewById(R.id.tvAddItem)
//
//        updateBtn = findViewById(R.id.updateBtn)
//
//        tvAddItem.setOnClickListener(){
//            Intent(this, ScannerActivity::class.java).also {
//                startActivityForResult(it,SECOND_ACTIVITY_REQUEST_CODE)
//            }
//        }


        // add a divider after each item for more clarity

        groceryAdapter = ProductInWardAdapters(productList, applicationContext)
        val horizontalLayoutManager =
            LinearLayoutManager(this@InwardProductActivity, LinearLayoutManager.VERTICAL, false)
        groceryRecyclerView!!.setLayoutManager(horizontalLayoutManager)
        groceryRecyclerView!!.setAdapter(groceryAdapter)



        groceryRecyclerView!!.addItemDecoration(
            DividerItemDecoration(
                this@InwardProductActivity,
                LinearLayoutManager.VERTICAL
            )
        )

        updateBtn!!.setOnClickListener(){
            updateProducts(productList);
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

        var provider: WebServiceProvider =
            WebServiceProvider.retrofit.create(WebServiceProvider::class.java)
        provider!!.productSearch(list!!)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ProductDetailResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: ProductDetailResponse) {



                        Toast.makeText(this@InwardProductActivity, "suceess", Toast.LENGTH_SHORT).show()
                        finish()



                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    Toast.makeText(this@InwardProductActivity, "failure", Toast.LENGTH_SHORT).show()
                }
            })
    }
}