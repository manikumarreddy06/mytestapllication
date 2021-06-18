package com.myproject.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.medfin.Utils
import com.myproject.myapplication.databinding.ActivityHomepageBinding
import com.myproject.myapplication.databinding.ProfitActivtyBinding
import com.myproject.myapplication.inward.InwardProductActivity
import com.myproject.myapplication.model.ProductDetailResponse
import com.myproject.myapplication.model.ProfitResponseBean
import com.myproject.myapplication.network.PreferenceManager
import com.myproject.myapplication.network.WebServiceProvider
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable


class ProfitActivity: AppCompatActivity() {
    private lateinit var binding:ProfitActivtyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfitActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnTodaySales.setOnClickListener {
            Intent(this, ProfitdetailsActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.btnTodayProfit.setOnClickListener {
            Intent(this, ProfitdetailsActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.btnMonthlySales.setOnClickListener {
            Intent(this, ProfitdetailsActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.btnMonthlyProfit.setOnClickListener {
            Intent(this, ProfitdetailsActivity::class.java).also {
                startActivity(it)
            }
        }

        getSalesData()
    }

    private fun getSalesData() {

            Utils.showDialog(this@ProfitActivity,"Loading")
            val obj = JsonObject()
            obj.addProperty("storeId", PreferenceManager.instance(this).get(PreferenceManager.STORE_ID,""))


            var provider: WebServiceProvider =
                WebServiceProvider.retrofit.create(WebServiceProvider::class.java).also {
                    it.getProfictDetails(obj)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : SingleObserver<ProfitResponseBean> {
                            override fun onSubscribe(d: Disposable) {

                            }

                            override fun onSuccess(response: ProfitResponseBean) {
                                Utils.hideDialog()
                                if (response.isIsvalid()) {
                                   populateUI(response)
                                }
                                else {

                                }

                            }

                            override fun onError(e: Throwable) {
                                Utils.hideDialog()
                                e.printStackTrace()
                                Toast.makeText(this@ProfitActivity, "failure", Toast.LENGTH_SHORT).show()
                            }
                        })
                }

    }

    private fun populateUI(response: ProfitResponseBean) {

        binding.btnTodaySales.text=response.salesAmount
        binding.btnTodayProfit.text=response.profitAmount

    }

}