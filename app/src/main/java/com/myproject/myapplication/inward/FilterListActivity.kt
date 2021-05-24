package com.myproject.myapplication.inward

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.text.format.DateFormat
import android.view.View
import android.view.Window
import android.widget.*
import com.abdeveloper.library.MultiSelectDialog
import com.medfin.bean.FilterRequest
import com.medfin.bean.city.CityList
import com.medfin.bean.city.CityResponseBean
import com.medfin.bean.city.DocList
import com.medfin.bean.city.HospList
import com.medfin.bean.filterStatus.FilterStatus
import com.medfin.bean.filterStatus.FilterStatusBean
import com.medfin.bean.supervisor.SupervisorList
import com.medfin.bean.supervisor.SupervisorListResponseBean
import com.medfin.network.WebServiceProvider
import com.medfin.utils.DataUtils
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.complete_dialog_filter.*
import java.text.SimpleDateFormat
import java.util.*


class FilterListActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {

    var cal = Calendar.getInstance()
    var fromDate:String?=null
    var toDate:String?=null

    var supervisorList: MutableList<SupervisorList>? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_ACTION_BAR)

        setContentView(R.layout.complete_dialog_filter)


        var filterFlag = intent.getBooleanExtra("FILTER_FLAG",false)
        if(filterFlag!!) {
            saveFilterRequest = intent!!.extras!!.get("FILTER_DATA") as FilterRequest
        }
        val submitButton = findViewById<Button>(R.id.btnSubmit)
        val cancelButton = findViewById<Button>(R.id.btnCancel)


        getCityListApi()
        getFilterStatusApi()
        getSupervisorList()

        toggle?.setOnCheckedChangeListener { group, checkedId ->
            if (R.id.rbYes == checkedId) {
                leadAssignementType = "yes";
            } else {
                leadAssignementType = "no";
            }
        }
        submitButton.setOnClickListener { v ->


            var mobileNumber:String?=null
            var custName:String?=null
            var doctorId:String?=null
            var hospitalId:String?=null
            var status:String?=null
            var supervisorId:String?=null
            var city:String?=selectedCityId

            doctorId=selectedDoctorList
            hospitalId=selectedHospitalList


            mobileNumber=etInput.text.toString()
            custName=etInputCustomerName.text.toString()

            if (spStatus.selectedItemPosition != 0) {
                status=statusList!![spStatus.selectedItemPosition-1].name
            }
            if (spSupervisor.selectedItemPosition != 0) {
                supervisorId=supervisorList!![spSupervisor.selectedItemPosition-1].userId.toString()
            }
            var leadId=etInputLeadId.text.toString()
            var filterRequest: FilterRequest?=null
            if(saveFilterRequest!=null){
                filterRequest=saveFilterRequest
            }
            else{
                filterRequest= FilterRequest()
            }
            filterRequest!!.filterCity=city
            filterRequest!!.selectedStatusId=status
            filterRequest!!.fromDate=fromDate
            filterRequest!!.toDate=toDate
            filterRequest!!.leadId=leadId
            filterRequest!!.supervisorId=supervisorId
            filterRequest!!.leadAssignment=leadAssignementType

            filterRequest!!.mobileNumber=mobileNumber
            filterRequest!!.customerName=custName

            if(doctorId!=null) {
                filterRequest!!.selectedDoctorId = doctorId
                filterRequest!!.displayStringDoctors=displayStringDoctors
            }
            if(hospitalId!=null) {
                filterRequest!!.selectedHospitalId = hospitalId
                filterRequest!!.displayStringHospitals=displayStringHospitals
            }


            val resultIntent = Intent()
            resultIntent.putExtra("FILTER_DATA", filterRequest)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        cancelButton.setOnClickListener { v ->
            val resultIntent = Intent()

            setResult(Activity.RESULT_CANCELED, resultIntent)
            finish()
        }
        imCancel.setOnClickListener{ v ->
            finish()
        }

        btnAssigned.setOnClickListener { v ->


            var mobileNumber:String?=null
            var custName:String?=null
            var doctorId:String?=null
            var hospitalId:String?=null
            var status:String?=null
            var supervisorId:String?=null
            var city:String?=selectedCityId


            doctorId=selectedDoctorList
            hospitalId=selectedHospitalList


            mobileNumber=etInput.text.toString()
            custName=etInputCustomerName.text.toString()

            if (spStatus.selectedItemPosition != 0) {
                status=statusList!![spStatus.selectedItemPosition-1].name
            }
            if (spSupervisor.selectedItemPosition != 0) {
                supervisorId=supervisorList!![spSupervisor.selectedItemPosition-1].userId.toString()
            }
            var leadId=etInputLeadId.text.toString()
            var filterRequest: FilterRequest?=null
            if(saveFilterRequest!=null){
                filterRequest=saveFilterRequest
            }
            else{
                filterRequest= FilterRequest()
            }
            filterRequest!!.filterCity=city
            filterRequest!!.selectedStatusId=status
            filterRequest!!.fromDate=fromDate
            filterRequest!!.toDate=toDate
            filterRequest!!.leadId=leadId
            filterRequest!!.supervisorId=supervisorId
            filterRequest!!.leadAssignment=leadAssignementType

            filterRequest!!.mobileNumber=mobileNumber
            filterRequest!!.customerName=custName

            if(doctorId!=null) {
                filterRequest!!.selectedDoctorId = doctorId
                filterRequest!!.displayStringDoctors=displayStringDoctors
            }
            if(hospitalId!=null) {
                filterRequest!!.selectedHospitalId = hospitalId
                filterRequest!!.displayStringHospitals=displayStringHospitals
            }


            val resultIntent = Intent()
            resultIntent.putExtra("FILTER_DATA", filterRequest)

            setResult(100, resultIntent)
            finish()
        }


        // create an OnDateSetListener
        val fromDateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView(tvFromDate,true)
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        btnFromDate!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@FilterListActivity,
                        fromDateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })



        // create an OnDateSetListener
        val toDateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView(tvToDate,false)
            }
        }
        btnToDate!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@FilterListActivity,
                        toDateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })


        fromDate = DateFormat.format("yyyy-MM-dd", Date()).toString()
        toDate = DateFormat.format("yyyy-MM-dd", Date()).toString()
        tvToDate.text=toDate
        tvFromDate.text=fromDate


        if(saveFilterRequest!=null){
            if(!TextUtils.isEmpty(saveFilterRequest!!.leadId)){
                etInputLeadId.setText(saveFilterRequest!!.leadId)
            }

            if(!TextUtils.isEmpty(saveFilterRequest!!.selectedHospitalId)){
                previousSelectedHospitalList= DataUtils.prepareList(saveFilterRequest!!.selectedHospitalId)
                displayStringHospitals=saveFilterRequest!!.displayStringHospitals
            }

            if(!TextUtils.isEmpty(saveFilterRequest!!.selectedDoctorId)){
                previousSelectedDocList= DataUtils.prepareList(saveFilterRequest!!.selectedDoctorId)
                displayStringDoctors=saveFilterRequest!!.displayStringDoctors
            }

            if(!TextUtils.isEmpty(saveFilterRequest!!.leadAssignment)){
                if (saveFilterRequest!!.leadAssignment.equals("yes")){
                    rbYes.isChecked=true
                    rbNo.isChecked=false
                }
                else if (saveFilterRequest!!.leadAssignment.equals("no")){
                    rbYes.isChecked=false
                    rbNo.isChecked=true
                }
            }

            if(!TextUtils.isEmpty(saveFilterRequest!!.fromDate)){
                fromDate=saveFilterRequest!!.fromDate
                tvFromDate.text=fromDate
            }

            if(!TextUtils.isEmpty(saveFilterRequest!!.toDate)){
                toDate=saveFilterRequest!!.toDate
                tvToDate.text=toDate
            }

            if(!TextUtils.isEmpty(saveFilterRequest!!.customerName)){
                etInputCustomerName.setText(saveFilterRequest!!.customerName)
            }
            if(!TextUtils.isEmpty(saveFilterRequest!!.mobileNumber)){
                etInput.setText(saveFilterRequest!!.mobileNumber)
            }
        }

    }

    interface Submit {
        fun onSubmit(filterRequest: FilterRequest)
    }

    interface Cancel {
        fun onCancel()
    }


    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.spCity -> {
                if (position != 0) {
                    Logger.d("onItemSelected", "position " + position)

                    containerDoctor.visibility= View.VISIBLE
                    containerHosiptal.visibility= View.VISIBLE
                    selectedCityId=cityList!![position-1].name
                    updateDoctorsList(cityList!![position-1]!!.docList)
                    updateHospitalList(cityList!![position-1]!!.hospList)

                }
            }
            R.id.spStatus -> {
                if (position != 0) {
                    Logger.d("onItemSelected", "position " + position)
                }
            }



        }
    }


    fun updateCityList(data: MutableList<CityList>) {
        cityList=data


        val outlet: MutableList<String> = ArrayList()
        outlet.add("Select")
        for (item in data)
            outlet.add(item.name!!)
        val adapter = ArrayAdapter<String>(this@FilterListActivity, android.R.layout.simple_spinner_dropdown_item,outlet )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCity.adapter = adapter
        spCity.onItemSelectedListener = this

        if(saveFilterRequest!=null && !TextUtils.isEmpty(saveFilterRequest!!.filterCity)){
            var selectedPosition:Int=0
            var mPosition:Int=0
            for (item in cityList!!){
                if(item.name.equals(saveFilterRequest!!.filterCity,false)){
                    selectedPosition=mPosition+1
                }
                else{
                    mPosition = mPosition+1
                }
            }
            if (selectedPosition>0) {
                spCity.setSelection(selectedPosition)
            }

        }


    }

    fun updateDoctorsList(data: MutableList<DocList>) {
        doctorList = data
        val outlet: MutableList<String> = ArrayList()
        for (item in data)
            outlet.add(item.name!!)

        val listOfDoctors =DataUtils.prepareMultiList(data)

        var preSelectedDoctors = ArrayList<Int>()

        if (previousSelectedDocList != null && previousSelectedDocList!!.size > 0) {
            preSelectedDoctors = DataUtils.preSelectIDsList(previousSelectedDocList!!)
            tvSelectedDoctor.visibility = View.VISIBLE
            tvSelectedDoctor.text=displayStringDoctors
        }
        else{
            tvSelectedDoctor.visibility = View.GONE
        }

        //MultiSelectModel
        var multiSelectDialogDoctor = MultiSelectDialog()
                .title("Select Doctors") //setting title for dialog
                .titleSize(20.0f)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMinSelectionLimit(1) //you can set minimum checkbox selection limit (Optional)
                .setMaxSelectionLimit(listOfDoctors.size) //you can set maximum checkbox selection limit (Optional)
                .preSelectIDsList(preSelectedDoctors) //List of ids that you need to be selected
                .multiSelectList(listOfDoctors) // the multi select model list with ids and name
                .onSubmit(object : MultiSelectDialog.SubmitCallbackListener {
                    override fun onSelected(selectedIds: ArrayList<Int>?, selectedNames: ArrayList<String>?, dataString: String?) {

                        displayStringDoctors=dataString
                        selectedDoctorList=""
                        for (i in selectedIds!!) {
                            if (!TextUtils.isEmpty(selectedDoctorList)) {
                                selectedDoctorList = selectedDoctorList + "," + i.toString()
                            } else {
                                selectedDoctorList = "" + i.toString()
                            }


                        }
                        tvSelectedDoctor.text=dataString
                        tvSelectedDoctor.visibility = View.VISIBLE
                    }

                    override fun onCancel() {
                    }
                })


        spDoc.setOnClickListener() {
            multiSelectDialogDoctor.show(supportFragmentManager, "multiSelectDialog");
        }


    }

    fun updateHospitalList(data: MutableList<HospList>) {

        hospitalList=data

        val outlet: MutableList<String> = ArrayList()
        for (item in hospitalList!!)
            outlet.add(item.name!!)

        val listOfHospital =DataUtils.prepareMultiListForHospital(data)

        var preSelectedHospitals = ArrayList<Int>()

        if (previousSelectedHospitalList != null && previousSelectedHospitalList!!.size > 0) {
            preSelectedHospitals = DataUtils.preSelectIDsList(previousSelectedHospitalList!!)
            tvSelectedHospital.visibility = View.VISIBLE
            tvSelectedHospital.text=displayStringHospitals

        }
        else{
            tvSelectedHospital.visibility = View.GONE
        }

        //MultiSelectModel
        var multiSelectDialogHospital = MultiSelectDialog()
                .title("Select Hospital") //setting title for dialog
                .titleSize(15.0f)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMinSelectionLimit(1) //you can set minimum checkbox selection limit (Optional)
                .setMaxSelectionLimit(listOfHospital.size) //you can set maximum checkbox selection limit (Optional)
                .preSelectIDsList(preSelectedHospitals) //List of ids that you need to be selected
                .multiSelectList(listOfHospital) // the multi select model list with ids and name
                .onSubmit(object : MultiSelectDialog.SubmitCallbackListener {
                    override fun onSelected(selectedIds: ArrayList<Int>?, selectedNames: ArrayList<String>?, dataString: String?) {
                        displayStringHospitals=dataString
                        selectedHospitalList=""
                        for (i in selectedIds!!) {
                            if (!TextUtils.isEmpty(selectedHospitalList)) {
                                selectedHospitalList = selectedHospitalList + "," + i.toString()
                            } else {
                                selectedHospitalList = "" + i.toString()
                            }


                        }
                        tvSelectedHospital.text=dataString
                        tvSelectedHospital.visibility = View.VISIBLE
                    }

                    override fun onCancel() {
                    }
                })


        spHos.setOnClickListener() {
            multiSelectDialogHospital.show(supportFragmentManager, "multiSelectDialog");
        }




    }

    fun updateStatusList(data: MutableList<FilterStatus>) {

        statusList=data

        val outlet: MutableList<String> = ArrayList()
        outlet.add("Select")
        for (item in statusList!!)
            outlet.add(item.name)
        val adapter = ArrayAdapter<String>(this@FilterListActivity, android.R.layout.simple_spinner_dropdown_item,outlet )
        spStatus.adapter = adapter
        spStatus.onItemSelectedListener = this

        if(saveFilterRequest!=null && !TextUtils.isEmpty(saveFilterRequest!!.selectedStatusId)){
            var selectedPosition:Int=0
            var mPosition:Int=0
            for (item in statusList!!){
                if(item.name.equals(saveFilterRequest!!.selectedStatusId,false)){
                    selectedPosition=mPosition+1
                }
                else{
                    mPosition = mPosition+1
                }
            }
            if (selectedPosition>0) {
                spStatus.setSelection(selectedPosition)
            }

        }
    }


    private fun getFilterStatusApi() {
        pbStatus.visibility= View.VISIBLE
        provider!!.getFilterStatusList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<FilterStatusBean> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onSuccess(response: FilterStatusBean) {
                        pbStatus.visibility= View.GONE
                        if (response.statusCode == 200 && response.data!=null) {
                            updateStatusList(response.data)
                        }
                        else if(!TextUtils.isEmpty(response.message)){
                            Utils.toast(response.message, this@FilterListActivity)

                        }else {
                            Utils.toast(getString(R.string.something_went_wrong),  this@FilterListActivity)

                        }

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        pbStatus.visibility= View.GONE
                        Utils.toast(getString(R.string.something_went_wrong), this@FilterListActivity)
                    }
                })

    }




    private fun getCityListApi() {
        pbStatus.visibility= View.VISIBLE
        provider!!.getCityList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<CityResponseBean> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onSuccess(response: CityResponseBean) {
                        pbStatus.visibility= View.GONE
                        if (response.statusCode == 200 && response.data!=null) {
                            updateCityList(response.data)
                        }
                        else if(!TextUtils.isEmpty(response.message)){
                            Utils.toast(response.message, this@FilterListActivity)

                        }else {
                            Utils.toast(getString(R.string.something_went_wrong),  this@FilterListActivity)

                        }

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        pbStatus.visibility= View.GONE
                        Utils.toast(getString(R.string.something_went_wrong), this@FilterListActivity)
                    }
                })

    }

    private fun updateDateInView(tvFromDate: TextView, fromDateFlag:Boolean) {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        tvFromDate!!.text = sdf.format(cal.getTime())
        if(fromDateFlag)
        {
            fromDate=sdf.format(cal.getTime()).toString()
        }
        else{

            toDate=sdf.format(cal.getTime()).toString()
        }
    }

    private fun getSupervisorList() {
        pbSupervisor.visibility= View.VISIBLE
        provider!!.getSupervisorList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<SupervisorListResponseBean> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onSuccess(response: SupervisorListResponseBean) {
                        pbSupervisor.visibility= View.GONE
                        if (response.statusCode == 200 && response.data!=null) {
                            updateSupervisorList(response.data)
                        }
                        else if(!TextUtils.isEmpty(response.message)){
                            Utils.toast(response.message, this@FilterListActivity)

                        }else {
                            Utils.toast(getString(R.string.something_went_wrong),  this@FilterListActivity)

                        }

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        pbSupervisor.visibility= View.GONE
                        Utils.toast(getString(R.string.something_went_wrong), this@FilterListActivity)
                    }
                })

    }


    fun updateSupervisorList(data: MutableList<SupervisorList>) {

        supervisorList=data

        val outlet: MutableList<String> = ArrayList()
        outlet.add("Select")
        for (item in supervisorList!!)
            outlet.add(item.name)
        val adapter = ArrayAdapter<String>(this@FilterListActivity, android.R.layout.simple_spinner_dropdown_item,outlet )
        Utils.Companion.setHeightForSpinner(spSupervisor,outlet)
        spSupervisor.adapter = adapter
        spSupervisor.onItemSelectedListener = this

        if(saveFilterRequest!=null && !TextUtils.isEmpty(saveFilterRequest!!.supervisorId)){
            var selectedPosition:Int=0
            var mPosition:Int=0
            for (item in supervisorList!!){
                if(item.userId.toString().equals(saveFilterRequest!!.supervisorId,false)){
                    selectedPosition=mPosition+1
                }
                else{
                    mPosition = mPosition+1
                }
            }
            if (selectedPosition>0) {
                spSupervisor.setSelection(selectedPosition)
            }

        }
    }

}

