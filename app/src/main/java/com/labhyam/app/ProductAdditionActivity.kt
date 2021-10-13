package com.labhyam.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.gson.JsonObject
import com.labhyam.app.databinding.ActivityProductadditionactivityBinding
import com.labhyam.app.model.BaseResponse
import com.labhyam.app.network.PreferenceManager
import com.labhyam.app.network.WebServiceProvider
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
        binding.etmanufacturedtype?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedPosition = position
            }
       }
        binding.btnadd.setOnClickListener {

            var etproductname = binding.etselectvaiant.text.toString()

            var etproductqty = binding.etProductQty.text.toString()

            var etProcPrice = binding.etProcPrice.text.toString().toFloat()

            var etInputSellPrice = binding.etInputSellPrice.text.toString().toFloat()

            var barcode = binding.etbarcode.text.toString()

            var brandName = binding.etBrandName.text.toString()








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
                    " type should be selected ",
                    this@ProductAdditionActivity
                )
           }
            else if(selectedPosition==-1) {
                   Utils.toast(
                       "unit type should be selected ",
                       this@ProductAdditionActivity
                   )
            }
            else if (TextUtils.isEmpty(etProcPrice.toString().toFloat().toString())) {
                Utils.toast(
                    "procurement Price should be  more than zero",
                    this@ProductAdditionActivity
                )
            } else if (TextUtils.isEmpty(etInputSellPrice.toString().toFloat().toString())) {
                Utils.toast("sellPrice should be  more than zero", this@ProductAdditionActivity)

            } else {
                var themes =
                    this@ProductAdditionActivity.resources.getStringArray(R.array.unit_types)
               var etunittype = themes[selectedPosition]


                this@ProductAdditionActivity.resources.getStringArray(R.array.manufactured_type)
                var etmanufacturedtype =themes[selectedPosition]









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



                obj.addProperty("barcode", barcode)
                obj.addProperty("productName", etproductname)
                obj.addProperty("brandname", brandName)
                 obj.addProperty("storeId", etproductqty)
                obj.addProperty("unitType", etunittype)
                obj.addProperty("manufacturedtype",etmanufacturedtype)
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
                                        finish()

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





