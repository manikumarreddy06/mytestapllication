package com.myproject.myapplication.inward

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.medfin.Utils
import com.myproject.myapplication.HomepageActivity
import com.myproject.myapplication.MainActivity
import com.myproject.myapplication.ProductUtils
import com.myproject.myapplication.adapters.CheckoutLineItemAdapter
import com.myproject.myapplication.adapters.ProductInWardAdapters
import com.myproject.myapplication.databinding.CheckoutActivityBinding
import com.myproject.myapplication.model.ProductDetailResponse
import com.myproject.myapplication.model.ProductVariant
import com.myproject.myapplication.network.PreferenceManager
import com.myproject.myapplication.network.WebServiceProvider
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class CheckoutActivity : AppCompatActivity() {



    private var productList: MutableList<ProductVariant> = ArrayList()
    private var groceryAdapter: CheckoutLineItemAdapter? = null


    private lateinit var binding:CheckoutActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CheckoutActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)




        productList=ProductUtils.instance(this).productList
        groceryAdapter = CheckoutLineItemAdapter(productList, applicationContext)
        val horizontalLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.setLayoutManager(horizontalLayoutManager)
        binding.rvContent.setAdapter(groceryAdapter)



        binding.rvContent.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        binding.checkoutBtn!!.setOnClickListener(){
            if(groceryAdapter!=null) {
                productList = groceryAdapter!!.productItems
                updateProducts(productList);
            }
        }

        if(ProductUtils.instance(this).isOutOrderTypeFlag){
            binding.tvHeaderTitle.text="Product Checkout"
        }
        else{
            binding.tvHeaderTitle.text="Product Addition"
        }

        binding.TvItemCount.text="Item Count: ${productList.size}"
        binding.TvItemPrice.text="Total Price: ${ProductUtils.instance(this).totalProcumentPrice}"


    }




    private fun updateProducts(productList: MutableList<ProductVariant>) {

        var list: MutableList<AddProduct?>? = ArrayList()

        val storeId=PreferenceManager.instance(this@CheckoutActivity).get(PreferenceManager.STORE_ID,"1").toLong()
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
                        Intent(this@CheckoutActivity, HomepageActivity::class.java).also {
                            it.flags=Intent.FLAG_ACTIVITY_NEW_TASK
                            it.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK

                            startActivity(it)
                            finish()
                        }

                    }

                    override fun onError(e: Throwable) {
                        Utils.hideDialog()
                        e.printStackTrace()
                        Toast.makeText(this@CheckoutActivity, "failure", Toast.LENGTH_SHORT).show()
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
                        Intent(this@CheckoutActivity, HomepageActivity::class.java).also {
                            it.flags=Intent.FLAG_ACTIVITY_NEW_TASK
                            it.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK

                            startActivity(it)
                            finish()
                        }

                    }

                    override fun onError(e: Throwable) {
                        Utils.hideDialog()
                        e.printStackTrace()
                        Toast.makeText(this@CheckoutActivity, "failure", Toast.LENGTH_SHORT).show()
                    }
                })
        }

    }
}