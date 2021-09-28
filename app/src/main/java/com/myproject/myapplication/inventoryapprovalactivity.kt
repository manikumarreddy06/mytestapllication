package com.myproject.myapplication

import android.R
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.myproject.myapplication.adapters.PendingInventoryAdapters
import com.myproject.myapplication.databinding.ActivityApprovalinventoryBinding
import com.myproject.myapplication.model.*
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
        groceryAdapter = PendingInventoryAdapters(productList, applicationContext,object : PendingInventoryAdapters.RecyclerViewClickListener {
            override fun onClick(product: NegativeValue?) {

                confirmationDialog(product!!)

            }
        })
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

        binding.backButton.setOnClickListener(){
            finish()
        }

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
                        updateAdapaters(response.negativeValues)
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

        if(productInfo!=null) {
            groceryAdapter!!.setData(productInfo!!);
        }
        else{
            groceryAdapter!!.clearData();
        }
        groceryAdapter!!.notifyDataSetChanged()


    }






    private fun confirmationDialog(product: NegativeValue) {

        AlertDialog.Builder(this)
            .setTitle("Product Approval")
            .setMessage("Are you sure you want to Approval  "+product.productName) // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton(
                R.string.yes
            ) { dialog, which ->
                updateProductStatus(product.variantId)
            } // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton(R.string.no, null)
            .setIcon(R.drawable.ic_dialog_alert)
            .show()
    }

    private fun updateProductStatus(productvariant:String) {


        val storeId=PreferenceManager.instance(this@inventoryapprovalactivity).get(PreferenceManager.STORE_ID,"1").toString()

        val obj = JsonObject()
        obj.addProperty("storeId", storeId)

        obj.addProperty("variantId", productvariant)

        Utils.showDialog(this,"Loading")
        var provider: WebServiceProvider =
            WebServiceProvider.retrofit.create(WebServiceProvider::class.java)
        provider.approvedProducts(obj)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<BaseResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: BaseResponse) {
                    Utils.hideDialog()
                    if(response.isIsvalid) {
                        Toast.makeText(this@inventoryapprovalactivity, "updated Successfully", Toast.LENGTH_SHORT).show()
                        getInventory()
                    }
                    else if(!response.isIsvalid && !TextUtils.isEmpty(response.message)) {
                        Toast.makeText(this@inventoryapprovalactivity, response.message, Toast.LENGTH_SHORT).show()
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


}
