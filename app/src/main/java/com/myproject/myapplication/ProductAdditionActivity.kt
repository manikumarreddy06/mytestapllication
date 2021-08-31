package com.myproject.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.gson.JsonObject
import com.medfin.Utils
import com.myproject.myapplication.databinding.ActivityProductadditionactivityBinding
import com.myproject.myapplication.model.BaseResponse
import com.myproject.myapplication.network.PreferenceManager
import com.myproject.myapplication.network.WebServiceProvider
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class ProductAdditionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductadditionactivityBinding

    private var selectedPosition=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductadditionactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.etunittype?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedPosition=position
            }

        }
        binding.btnadd.setOnClickListener {

            var etproductname = binding.etselectvaiant.text.toString()

            var etproductqty = binding.etProductQty.text.toString()

            var etProcPrice = binding.etProcPrice.text.toString()

            var etInputSellPrice = binding.etInputSellPrice.text.toString()

            if (TextUtils.isEmpty(etproductname.toString())) {
                Utils.toast("product name can't be empty", this@ProductAdditionActivity)
            } else if (TextUtils.isEmpty(etproductqty.toString())) {
                Utils.toast("quantity should be  more than zero", this@ProductAdditionActivity)
            } else if (TextUtils.isEmpty(etproductqty.toString())) {
                Utils.toast(
                    "procurment Price should be  more than zero",
                    this@ProductAdditionActivity
                )}
                else if(selectedPosition==-1) {
                    Utils.toast(
                        "unit type should be selected ",
                        this@ProductAdditionActivity
                    )
            } else if (TextUtils.isEmpty(etProcPrice.toString())) {
                Utils.toast(
                    "procurment Price should be  more than zero",
                    this@ProductAdditionActivity
                )
            } else if (TextUtils.isEmpty(etInputSellPrice.toString())) {
                Utils.toast("sellPrice should be  more than zero", this@ProductAdditionActivity)

            } else {

                var themes = this@ProductAdditionActivity.resources.getStringArray(R.array.unit_types)
                var etunittype = themes[selectedPosition]
                Utils.showDialog(this@ProductAdditionActivity, "Loading")
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
                obj.addProperty("brandname", etproductname)
                 obj.addProperty("storeId", etproductqty)
                obj.addProperty("unitType", etunittype)
                obj.addProperty("unit", etproductqty)
                obj.addProperty("mrp", etProcPrice)
                obj.addProperty("procurementprice", etProcPrice)
                obj.addProperty("selling price", etInputSellPrice)
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

                                        Toast.makeText(this@ProductAdditionActivity, "product added successfully", Toast.LENGTH_SHORT).show()

                                    } else {
                                        Toast.makeText(this@ProductAdditionActivity, "product already available", Toast.LENGTH_SHORT).show()
                                    }

                                }

                                override fun onError(e: Throwable) {
                                    Utils.hideDialog()
                                    e.printStackTrace()
                                    Toast.makeText(
                                        this@ProductAdditionActivity,
                                        "failure",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                    }
            }
            binding.btnCancel.setOnClickListener {
                Toast.makeText(this@ProductAdditionActivity, "cancelled", Toast.LENGTH_SHORT).show()
            }


        }

        binding.btnCancel.setOnClickListener(){
            finish()
        }

        binding.btnCancel.setOnClickListener(){
            finish()
        }
        binding.imCancel.setOnClickListener(){
            finish()
        }


    }


}





