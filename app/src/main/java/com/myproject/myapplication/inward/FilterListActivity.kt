package com.myproject.myapplication.inward

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.medfin.Logger
import com.myproject.myapplication.R
import com.myproject.myapplication.model.ProductVariant
import java.util.*


class FilterListActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {


    var supervisorList: MutableList<ProductVariant>? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_ACTION_BAR)

        setContentView(R.layout.complete_dialog_filter)


        val submitButton = findViewById<Button>(R.id.btnSubmit)
        val cancelButton = findViewById<Button>(R.id.btnCancel)


        //updateProductvariantList();


        submitButton.setOnClickListener { v ->



        }

        cancelButton.setOnClickListener { v ->
            val resultIntent = Intent()

            setResult(Activity.RESULT_CANCELED, resultIntent)
            finish()
        }





    }

    interface Submit {
       // fun onSubmit(filterRequest: FilterRequest)
    }

    interface Cancel {
        fun onCancel()
    }


    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {

            R.id.spVariant -> {
                if (position != 0) {
                    Logger.d("onItemSelected", "position " + position)
                }
            }



        }
    }




    fun updateSupervisorList(data: MutableList<ProductVariant>) {

        supervisorList=data

        val outlet: MutableList<String> = ArrayList()
        outlet.add("Select")
        for (item in supervisorList!!)
            outlet.add(item.productVariantName)
        val adapter = ArrayAdapter<String>(this@FilterListActivity, android.R.layout.simple_spinner_dropdown_item,outlet )
        ///Utils.Companion.setHeightForSpinner(spSupervisor,outlet)
//        spv.adapter = adapter
//        spVariant.onItemSelectedListener = this
    }

}

