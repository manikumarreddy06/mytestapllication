package com.myproject.myapplication
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.myproject.myapplication.adapters.CheckoutLineItemAdapter
import com.myproject.myapplication.adapters.ProfitDetailLineAdapters
import com.myproject.myapplication.databinding.CheckoutActivityBinding
import com.myproject.myapplication.databinding.ProfitDetailsActivityBinding
import com.myproject.myapplication.model.ProductVariant
import com.myproject.myapplication.model.ProfitDetailsResponseBean
import com.myproject.myapplication.model.ProfitResponseBean
import com.myproject.myapplication.model.SalesInfo
import com.myproject.myapplication.network.PreferenceManager
import com.myproject.myapplication.network.WebServiceProvider
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable


class ProfitdetailsActivity:AppCompatActivity() {


    private var productList: MutableList<SalesInfo> = ArrayList()
    private var groceryAdapter: ProfitDetailLineAdapters? = null

    private var type:String?=null

    private lateinit var binding: ProfitDetailsActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfitDetailsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        type=intent!!.extras!!.getString("Type")
        getSalesData()
        binding.backButton.setOnClickListener(){
            finish()
        }

    }


    private fun getSalesData() {

        Utils.showDialog(this@ProfitdetailsActivity, "Loading")
        val obj = JsonObject()
        obj.addProperty(
            "storeId",
            PreferenceManager.instance(this).get(PreferenceManager.STORE_ID, "")

        )

        obj.addProperty(
            "type",type

        )


        var provider: WebServiceProvider =
            WebServiceProvider.retrofit.create(WebServiceProvider::class.java).also {
                it.getProfictDetailsByType(obj)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : SingleObserver<ProfitDetailsResponseBean> {
                        override fun onSubscribe(d: Disposable) {

                        }

                        override fun onSuccess(response: ProfitDetailsResponseBean) {
                            Utils.hideDialog()
                            if (response.isIsvalid()) {
                                populateUI(response)
                            } else {

                            }

                        }

                        override fun onError(e: Throwable) {
                            Utils.hideDialog()
                            e.printStackTrace()
                            Toast.makeText(
                                this@ProfitdetailsActivity,
                                "failure",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }

    }

    public fun populateUI(response: ProfitDetailsResponseBean) {

        productList = response.salesInfo
        groceryAdapter = ProfitDetailLineAdapters(productList, applicationContext)
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





        binding.TvItemCount.text = "Item Count: ${response.sumofQuatity}"
        binding.TvItemPrice.text = "Profit : ${response.sumofProfit}"
        binding.TvSales.text = "Sales : ${response.sumofSales}"
    }
}
