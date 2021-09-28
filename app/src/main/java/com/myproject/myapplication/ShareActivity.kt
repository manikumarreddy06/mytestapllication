package com.myproject.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.myproject.myapplication.databinding.ProfitActivtyBinding
import com.myproject.myapplication.databinding.ShareActivityBinding
import com.myproject.myapplication.network.PreferenceManager
import com.myproject.myapplication.network.WebServiceProvider
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody
import java.net.URLEncoder

class ShareActivity:AppCompatActivity() {
    private lateinit var binding: ShareActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ShareActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var orderId=intent.getStringExtra("orderId");

        binding.btnShare.setOnClickListener(){


            var message:String="bill details are http://15.206.255.26:8080/api/order/export/pdf/"+orderId

            var whatsappIntent = Intent(Intent.ACTION_SEND)
            whatsappIntent.type = "text/plain";
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, message);
            try {
                startActivity(whatsappIntent);
            }
            catch (e:Exception){

                Utils.toast("Whatsapp have not been installed.",this)
            }
        }



        binding.backButton.setOnClickListener(){

            Intent(this@ShareActivity, HomepageActivity::class.java).also {
                it.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(it)
                finish()
            }
        }

        binding.btnHome.setOnClickListener(){
            Intent(this@ShareActivity, HomepageActivity::class.java).also {
                it.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(it)
                finish()
            }
        }

    }



    private fun getOrderDetails() {

        val storeId=
            PreferenceManager.instance(this@ShareActivity).get(PreferenceManager.STORE_ID,"1").toString()

        Utils.showDialog(this,"Loading")
        var provider: WebServiceProvider =
            WebServiceProvider.retrofit.create(WebServiceProvider::class.java)
        provider.pdfGenerator()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ResponseBody> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: ResponseBody) {
                    Utils.hideDialog()
                   // FileUtils.writeResponseBodyToDisk(response,this@ShareActivity)

                }

                override fun onError(e: Throwable) {
                    Utils.hideDialog()
                    e.printStackTrace()
                    Toast.makeText(this@ShareActivity, "failure", Toast.LENGTH_SHORT).show()
                }
            })

    }
}