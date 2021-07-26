package com.myproject.myapplication

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.medfin.Utils
import com.myproject.myapplication.adapters.InventoryAdapters
import com.myproject.myapplication.adapters.PendingInventoryAdapters
import com.myproject.myapplication.databinding.ActivityApprovalinventoryBinding
import com.myproject.myapplication.model.ApproveResponse
import com.myproject.myapplication.model.NegativeValue
import com.myproject.myapplication.model.ProductInfo
import com.myproject.myapplication.model.StoreInvResponseBean
import com.myproject.myapplication.network.PreferenceManager
import com.myproject.myapplication.network.WebServiceProvider
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class inventoryapprovalactivity: AppCompatActivity() {
    private lateinit var binding: ActivityApprovalinventoryBinding

    private var groceryAdapter: PendingInventoryAdapters? = null
    private val productList: MutableList<NegativeValue> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityApprovalinventoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        groceryAdapter = PendingInventoryAdapters(productList, applicationContext)
        val horizontalLayoutManager =
            LinearLayoutManager(
                this@inventoryapprovalactivity,
                LinearLayoutManager.VERTICAL,
                false
            )
        binding.rvContent.setLayoutManager(horizontalLayoutManager)
        binding.rvContent.setAdapter(groceryAdapter)

        binding.rvContent.addItemDecoration(
            DividerItemDecoration(
                this@inventoryapprovalactivity,
                LinearLayoutManager.VERTICAL
            )
        )
        getInventory()

    }
    private fun getInventory() {

        val storeId=PreferenceManager.instance(this@inventoryapprovalactivity).get(PreferenceManager.STORE_ID,"1").toString()

        Utils.showDialog(this,"Loading")
        var provider: WebServiceProvider =
            WebServiceProvider.retrofit.create(WebServiceProvider::class.java)
        provider.getapproveproduct(storeId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ApproveResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: ApproveResponse) {
                    Utils.hideDialog()
                    if(response.isvalid!!) {

                        Toast.makeText(this@inventoryapprovalactivity, "success", Toast.LENGTH_SHORT).show()
                        updateAdapaters(response.negativeValues)
                    }
                    else{
                        Toast.makeText(this@inventoryapprovalactivity, "product not found", Toast.LENGTH_SHORT).show()

                    }

                }

                override fun onError(e: Throwable) {
                    Utils.hideDialog()
                    e.printStackTrace()
                    Toast.makeText(this@inventoryapprovalactivity, "failure", Toast.LENGTH_SHORT).show()
                }

            })

    }

    private fun updateAdapaters(productInfo: List<NegativeValue>?) {

        groceryAdapter!!.setData(productInfo!!);
        groceryAdapter!!.notifyDataSetChanged()


         }


}
