package com.myproject.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.medfin.Utils
import com.myproject.myapplication.adapters.StoreListAdapter
import com.myproject.myapplication.databinding.ActivityStorelist2Binding
import com.myproject.myapplication.model.BaseResponse
import com.myproject.myapplication.model.City
import com.myproject.myapplication.model.Store
import com.myproject.myapplication.network.PreferenceManager
import com.myproject.myapplication.network.WebServiceProvider
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.ArrayList


class StorelistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStorelist2Binding

    private var selectedCityPosition = -1
    var mCityList: MutableList<City>?=null


    private var storeRecyclerView: RecyclerView? = null
    var storeList:List<Store> = ArrayList()
    var storeListAdapter: StoreListAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStorelist2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        getCityList()



        storeRecyclerView = findViewById<View>(R.id.etstorelist) as RecyclerView
        storeListAdapter = StoreListAdapter(storeList!!, applicationContext,object : StoreListAdapter.RecyclerViewClickListener {
            override fun onClick(product: Store?) {

            }
        });

        val horizontalLayoutManager =
            LinearLayoutManager(this@StorelistActivity, LinearLayoutManager.VERTICAL, false)
        storeRecyclerView!!.setLayoutManager(horizontalLayoutManager)
        storeRecyclerView!!.setAdapter(storeListAdapter)

        storeRecyclerView!!.addItemDecoration(
            DividerItemDecoration(
                this@StorelistActivity,
                LinearLayoutManager.VERTICAL
            )
        )

    }


    private fun getCityList() {

        val storeId=
            PreferenceManager.instance(this@StorelistActivity).get(PreferenceManager.STORE_ID,"1").toString()

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
                    if(response.isIsvalid) {
                        updatedCityList(response.cityList)
                    }
                    else if (!TextUtils.isEmpty(response.message)){
                        Toast.makeText(this@StorelistActivity, response.message, Toast.LENGTH_SHORT).show()

                    }
                    else{
                        Toast.makeText(this@StorelistActivity, "results not found", Toast.LENGTH_SHORT).show()

                    }

                }

                override fun onError(e: Throwable) {
                    Utils.hideDialog()
                    e.printStackTrace()
                    Toast.makeText(this@StorelistActivity, "failure", Toast.LENGTH_SHORT).show()
                }
            })

    }

    fun updatedCityList(cityList: MutableList<City>) {
        mCityList=cityList
        val outlet: MutableList<String> = ArrayList()
        outlet.add("Select")
        if(cityList !=null && cityList.size>0) {
            for (item in cityList)
                outlet.add(item.cityName!!)

            val adapter = ArrayAdapter<String>(this@StorelistActivity, android.R.layout.simple_spinner_dropdown_item, outlet)
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

                    if(selectedCityPosition>0){
                        getStoreList(mCityList!![selectedCityPosition-1].cityId)
                    }
                }

            }
        }
    }



    private fun getStoreList(cityId:String) {


        Utils.showDialog(this,"Loading")
        var provider: WebServiceProvider =
            WebServiceProvider.retrofit.create(WebServiceProvider::class.java)
        provider.getStoresBycity(cityId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<BaseResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: BaseResponse) {
                    Utils.hideDialog()
                    if(response.isIsvalid) {
                        updateAdapters(response.listOfStores)
                    }
                    else if (!TextUtils.isEmpty(response.message)){
                        Toast.makeText(this@StorelistActivity, response.message, Toast.LENGTH_SHORT).show()
                        updateEmptyAdapters()
                    }
                    else{
                        Toast.makeText(this@StorelistActivity, "results not found", Toast.LENGTH_SHORT).show()

                    }

                }

                override fun onError(e: Throwable) {
                    Utils.hideDialog()
                    e.printStackTrace()
                    Toast.makeText(this@StorelistActivity, "failure", Toast.LENGTH_SHORT).show()
                }
            })

    }

    private fun updateAdapters(productVariants: List<Store>) {

        if(productVariants!=null && productVariants!!.size>0){
            storeListAdapter!!.setData(productVariants!!)
        }
        else {
            storeList.isEmpty()
            storeListAdapter!!.setData(storeList )
        }

    }

    private fun updateEmptyAdapters() {
            storeList.isEmpty()
            storeListAdapter!!.setData(storeList )
    }
}