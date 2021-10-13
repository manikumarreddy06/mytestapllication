package com.labhyam.app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.labhyam.app.adapters.InventoryAdapters
import com.labhyam.app.databinding.ActivityInventoryBinding
import com.labhyam.app.model.ProductInfo
import com.labhyam.app.model.StoreInvResponseBean
import com.labhyam.app.network.PreferenceManager
import com.labhyam.app.network.WebServiceProvider
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class  InventoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInventoryBinding

    private var groceryAdapter: InventoryAdapters? = null

    private val productList: MutableList<ProductInfo> = ArrayList()

    override fun onResume() {
        super.onResume()

        getInventory();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInventoryBinding.inflate(layoutInflater)
        setContentView(binding.root)




        groceryAdapter = InventoryAdapters(productList, applicationContext)
        val horizontalLayoutManager =
            LinearLayoutManager(this@InventoryActivity, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.setLayoutManager(horizontalLayoutManager)
        binding.rvContent.setAdapter(groceryAdapter)



        binding.rvContent.addItemDecoration(
            DividerItemDecoration(
                this@InventoryActivity,
                LinearLayoutManager.VERTICAL
            )
        )

        binding.backButton.setOnClickListener(){
            finish()
        }
        getInventory();
        binding.alterInventory.setOnClickListener {
            Intent(this, inventoryapprovalactivity::class.java).also {
                startActivity(it)
            }
        }

    }

    private fun getInventory() {

        val storeId=PreferenceManager.instance(this@InventoryActivity).get(PreferenceManager.STORE_ID,"1").toString()

        Utils.showDialog(this,"Loading")
        var provider: WebServiceProvider =
            WebServiceProvider.retrofit.create(WebServiceProvider::class.java)
        provider.getInventory(storeId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<StoreInvResponseBean> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: StoreInvResponseBean) {
                    Utils.hideDialog()
                    if(response.isIsvalid()) {
                        updateAdapaters(response.productInfo)
                    }
                    else{
                        Toast.makeText(this@InventoryActivity, "product not found", Toast.LENGTH_SHORT).show()

                    }

                }

                override fun onError(e: Throwable) {
                    Utils.hideDialog()
                    e.printStackTrace()
                    Toast.makeText(this@InventoryActivity, "failure", Toast.LENGTH_SHORT).show()
                }
            })

    }

    private fun updateAdapaters(productInfo: List<ProductInfo>) {

        groceryAdapter!!.setData(productInfo);
        groceryAdapter!!.notifyDataSetChanged()

    }


}