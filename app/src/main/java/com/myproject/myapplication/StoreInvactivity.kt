package com.myproject.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.myproject.myapplication.adapters.InventoryAdapters
import com.myproject.myapplication.adapters.ProductInWardAdapters
import com.myproject.myapplication.databinding.ActivityStoreactivityBinding
import com.myproject.myapplication.model.ProductDetailResponse
import com.myproject.myapplication.model.ProductInfo
import com.myproject.myapplication.model.ProductVariant
import com.myproject.myapplication.model.StoreInvResponseBean
import com.myproject.myapplication.network.PreferenceManager
import com.myproject.myapplication.network.WebServiceProvider
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class StoreInvactivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoreactivityBinding

    private var groceryAdapter: InventoryAdapters? = null

    private val productList: MutableList<ProductInfo> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)



        groceryAdapter = InventoryAdapters(productList, applicationContext)
        val horizontalLayoutManager =
            LinearLayoutManager(this@StoreInvactivity, LinearLayoutManager.VERTICAL, false)
        binding.rvContent!!.setLayoutManager(horizontalLayoutManager)
        binding.rvContent!!.setAdapter(groceryAdapter)



        binding.rvContent!!.addItemDecoration(
            DividerItemDecoration(
                this@StoreInvactivity,
                LinearLayoutManager.VERTICAL
            )
        )
        getInventory();

    }

    private fun getInventory() {

        val storeId=PreferenceManager.instance(this@StoreInvactivity).get(PreferenceManager.STORE_ID,"1").toString()

        var provider: WebServiceProvider =
            WebServiceProvider.retrofit.create(WebServiceProvider::class.java)
        provider.getInventory(storeId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<StoreInvResponseBean> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: StoreInvResponseBean) {

                    if(response.isIsvalid()) {

                        Toast.makeText(this@StoreInvactivity, "suceess", Toast.LENGTH_SHORT).show()
                        updateAdapaters(response.productInfo)
                    }
                    else{
                        Toast.makeText(this@StoreInvactivity, "product not found", Toast.LENGTH_SHORT).show()

                    }

                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    Toast.makeText(this@StoreInvactivity, "failure", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun updateAdapaters(productInfo: List<ProductInfo>) {

        groceryAdapter!!.setData(productInfo);
        groceryAdapter!!.notifyDataSetChanged()
    }
}