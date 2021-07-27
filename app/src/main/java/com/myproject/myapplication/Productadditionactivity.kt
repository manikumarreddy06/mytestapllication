package com.myproject.myapplication

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.JsonObject
import com.medfin.Utils
import com.myproject.myapplication.databinding.ActivityProductadditionactivityBinding
import com.myproject.myapplication.inward.InwardProductActivity
import com.myproject.myapplication.model.BaseResponse
import com.myproject.myapplication.model.ProductDetailResponse
import com.myproject.myapplication.model.ProductDetails
import com.myproject.myapplication.model.ProductVariant
import com.myproject.myapplication.network.PreferenceManager
import com.myproject.myapplication.network.WebServiceProvider
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class Productadditionactivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductadditionactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductadditionactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.btnadd.setOnClickListener {

            var etproductname = binding.etselectvaiant.text.toString()

            var etproductqty = binding.etProductQty.text.toString()

            var etunittype = binding.etunittype.text.toString()

            var etProcPrice = binding.etProcPrice.text.toString()

            var etInputSellPrice = binding.etInputSellPrice.text.toString()

            if (TextUtils.isEmpty(etproductname.toString())) {
                Utils.toast("product name can't be empty", this@Productadditionactivity)
            } else if (TextUtils.isEmpty(etproductqty.toString())) {
                Utils.toast("quantity should be  more than zero", this@Productadditionactivity)
            } else if (TextUtils.isEmpty(etproductqty.toString())) {
                Utils.toast(
                    "procurment Price should be  more than zero",
                    this@Productadditionactivity
                )
            } else if (TextUtils.isEmpty(etunittype.toString())) {
                Utils.toast(
                    "unit type should be positive ",
                    this@Productadditionactivity
                )
            } else if (TextUtils.isEmpty(etProcPrice.toString())) {
                Utils.toast(
                    "procurment Price should be  more than zero",
                    this@Productadditionactivity
                )
            } else if (TextUtils.isEmpty(etInputSellPrice.toString())) {
                Utils.toast("sellPrice should be  more than zero", this@Productadditionactivity)

            } else {

                Utils.showDialog(this@Productadditionactivity, "Loading")
                val obj = JsonObject()

//                private String productName;//yes
//                private String brandName;//yes
//                private String unitType;//yes
//                private String unit;//yes
//                private String productSubCategory;
//                private String productCategory;
//                private String productFamily;
//                private String mrp;//yes
//                private String storeId;//yes
//                private String productImage;
//                private String sellingPrice;//yes
//                private String procPrice;//yes


                obj.addProperty("productName", etproductname)
                obj.addProperty("brandname",etproductname)
                obj.addProperty("storeId", etproductqty)
                obj.addProperty("unitType", etunittype)
                obj.addProperty("unit", etproductqty)
                obj.addProperty("mrp", etProcPrice)
                obj.addProperty("procurementprice",etProcPrice)
                obj.addProperty("selling price",etInputSellPrice)
                obj.addProperty(
                    "storeId",
                    PreferenceManager.instance(this).get(PreferenceManager.STORE_ID, "")
                )


                var provider: WebServiceProvider =
                    WebServiceProvider.retrofit.create(WebServiceProvider::class.java).also {
                        it.addCustomeProduct(obj)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(object : SingleObserver<BaseResponse> {
                                override fun onSubscribe(d: Disposable) {

                                }

                                override fun onSuccess(t: BaseResponse) {

                                    Utils.hideDialog()
                                    if (t.isIsvalid()) {

                                        Toast.makeText(this@Productadditionactivity, "product added successfully", Toast.LENGTH_SHORT).show()

                                    } else {

                                    }
                                }

                                override fun onError(e: Throwable) {
                                    Utils.hideDialog()
                                    e.printStackTrace()
                                    Toast.makeText(
                                        this@Productadditionactivity,
                                        "failure",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                    }
            }
            binding.btnCancel.setOnClickListener {
                Toast.makeText(this@Productadditionactivity, "cancelled", Toast.LENGTH_SHORT).show()
            }

        }

    }


}




