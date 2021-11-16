package com.labhyam.app

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.JsonObject
import com.labhyam.app.databinding.ActivityStorecreationBinding
import com.labhyam.app.databinding.ActivityUseronboardBinding
import com.labhyam.app.model.BaseResponse
import com.labhyam.app.model.City
import com.labhyam.app.model.UserType
import com.labhyam.app.network.PreferenceManager
import com.labhyam.app.network.WebServiceProvider
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.ArrayList

class UseronboardActivity :AppCompatActivity() {
    private lateinit var binding: ActivityUseronboardBinding
    private var selectedCityPosition = -1
    private var selectedUserTypePosition = -1

    var mCityList: MutableList<City>?=null
   var mUserList:String="Storemanager"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUseronboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCityList()
        //getUserTypes()

        binding.btnadd.setOnClickListener {

            var etstorename = binding.etStorename.text.toString()


            var etphonenumber = binding.etphonenum.text.toString()

            var etpassword = binding.etPassword1.text.toString()


            var etownername = binding.etownername.text.toString()

            var etaadahrnum=binding.etAadharnum.text.toString()

            var etlocation=binding.etlocation.text.toString()

            var etStoreType = binding.etStoreType.text.toString()

            if (selectedCityPosition == 0) {
                Utils.toast(
                    "city should be selected ",
                    this@UseronboardActivity
                )
            }
            else if (TextUtils.isEmpty(etstorename.toString())) {
                Utils.toast("storename name can't be empty", this@UseronboardActivity)
            }
            else if (TextUtils.isEmpty(etphonenumber.toString())) {
                Utils.toast(
                    "phone number can't be empty",
                    this@UseronboardActivity
                )
            }

            else if (TextUtils.isEmpty(etpassword.toString())) {
                Utils.toast(
                    "password can't be empty",
                    this@UseronboardActivity
                )

            }
            else if (TextUtils.isEmpty(etownername.toString())) {
                Utils.toast(
                    "owner name can't be empty",
                    this@UseronboardActivity
                )

            }
            else if (TextUtils.isEmpty(etaadahrnum.toString())) {
                Utils.toast(
                    "aadhar number name can't be empty",
                    this@UseronboardActivity
                )

            }
            else if (TextUtils.isEmpty(etlocation.toString())) {
                Utils.toast(
                    "Location required",
                    this@UseronboardActivity
                )

            }

            else  if (selectedUserTypePosition == 0) {
                Utils.toast(
                    "user type should be selected ",
                    this@UseronboardActivity
                )
            }
            else if (TextUtils.isEmpty(etStoreType.toString())) {
                Utils.toast(
                    "Store type can't be empty",
                    this@UseronboardActivity
                )
            }

            else {
                var cityId = mCityList!![selectedCityPosition-1].cityId

               //var userType = mUserList!![selectedUserTypePosition-1].userId

                Utils.showDialog(this@UseronboardActivity, "Loading")
                val obj = JsonObject()
                obj.addProperty("storeName", etstorename)
                obj.addProperty("cityId", cityId)
                obj.addProperty("phoneNumber", etphonenumber)
                obj.addProperty("password", etpassword)
                obj.addProperty("ownerName", etownername)
                obj.addProperty("aadharNumber",etaadahrnum)
                obj.addProperty("storeLocation",etlocation)
                obj.addProperty("storeType", etStoreType)
                obj.addProperty("userType", mUserList)
                obj.addProperty("createdby",""+ PreferenceManager.instance(this).get(
                    PreferenceManager.USER_ID,0L))
                var provider: WebServiceProvider
                WebServiceProvider.retrofit.create(WebServiceProvider::class.java).also {
                    it.addcustomerstore(obj)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : SingleObserver<BaseResponse> {
                            override fun onSubscribe(d: Disposable) {

                            }

                            override fun onSuccess(t: BaseResponse) {
                                Utils.hideDialog()
                                if (t.isIsvalid()){
                                    Toast.makeText(this@UseronboardActivity, "store created successfully", Toast.LENGTH_SHORT).show()
                                    Intent(this@UseronboardActivity, HomepageActivity::class.java).also {
                                        it.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                                        startActivity(it)
                                        finish()
                                    }
                                }
                                else if (!TextUtils.isEmpty(t.message)){
                                    Toast.makeText(this@UseronboardActivity, t.message, Toast.LENGTH_SHORT).show()

                                }
                                else{
                                    Toast.makeText(this@UseronboardActivity, "results not found", Toast.LENGTH_SHORT).show()

                                }
                            }

                            override fun onError(e: Throwable) {
                                Utils.hideDialog()
                                e.printStackTrace()
                                Toast.makeText(
                                    this@UseronboardActivity,
                                    "failure",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                }
            }
        }
        binding.btnCancel.setOnClickListener(){
            finish()
        }
    }



//    private fun getUserTypes() {
//
//        val storeId=
//            PreferenceManager.instance(this@UseronboardActivity).get(PreferenceManager.STORE_ID,"1").toString()
//
//        Utils.showDialog(this,"Loading")
//        var provider: WebServiceProvider =
//            WebServiceProvider.retrofit.create(WebServiceProvider::class.java)
//        provider.getUserTypeList()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : SingleObserver<BaseResponse> {
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onSuccess(response: BaseResponse) {
//                    Utils.hideDialog()
//                    if(response.isIsvalid()) {
//                        updateTypeList(response.userTypes)
//                    }
//                    else if (!TextUtils.isEmpty(response.message)){
//                        Toast.makeText(this@UseronboardActivity, response.message, Toast.LENGTH_SHORT).show()
//                    }
//                    else{
//                        Toast.makeText(this@UseronboardActivity, "results not found", Toast.LENGTH_SHORT).show()
//
//                    }
//
//                }
//
//                override fun onError(e: Throwable) {
//                    Utils.hideDialog()
//                    e.printStackTrace()
//                    Toast.makeText(this@UseronboardActivity, "failure", Toast.LENGTH_SHORT).show()
//                }
//            })
//
//    }

    private fun getCityList() {

        val storeId=
            PreferenceManager.instance(this@UseronboardActivity).get(PreferenceManager.STORE_ID,"1").toString()

        Utils.showDialog(this,"Loading")
        var provider: WebServiceProvider =
            WebServiceProvider.retrofit.create(WebServiceProvider::class.java)
        provider.getCityList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<BaseResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: BaseResponse) {
                    Utils.hideDialog()
                    if(response.isIsvalid()) {
                        updatedcitylist(response.cityList)
                    }
                    else if (!TextUtils.isEmpty(response.message)){
                        Toast.makeText(this@UseronboardActivity, response.message, Toast.LENGTH_SHORT).show()

                    }
                    else{
                        Toast.makeText(this@UseronboardActivity, "results not found", Toast.LENGTH_SHORT).show()

                    }

                }

                override fun onError(e: Throwable) {
                    Utils.hideDialog()
                    e.printStackTrace()
                    Toast.makeText(this@UseronboardActivity, "failure", Toast.LENGTH_SHORT).show()
                }
            })

    }

    fun updatedcitylist(cityList: MutableList<City>) {
        mCityList=cityList
        val outlet: MutableList<String> = ArrayList()
        outlet.add("Select")
        if(cityList !=null && cityList.size>0) {
            for (item in cityList)
                outlet.add(item.cityName!!)

            val adapter = ArrayAdapter<String>(this@UseronboardActivity, R.layout.simple_spinner_dropdown_item, outlet)
            binding.etCityList.adapter=adapter
            binding.etCityList?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedCityPosition = position
                }

            }
        }
    }


//    fun updateTypeList(usertypeList: MutableList<UserType>) {
//        mUserList=usertypeList
//        val outlet: MutableList<String> = ArrayList()
//        outlet.add("Select")
//        if(usertypeList !=null && usertypeList.size>0) {
//            for (item in usertypeList)
//                outlet.add(item.userType!!)
//
//            val adapter = ArrayAdapter<String>(this@UseronboardActivity, R.layout.simple_spinner_dropdown_item, outlet)
//            binding.etUsertype.adapter=adapter
//            binding.etUsertype?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//
//                }
//
//                override fun onItemSelected(
//                    parent: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    id: Long
//                ) {
//                    selectedUserTypePosition = position
//                }
//
//            }
//        }
//    }
}
