package com.myproject.myapplication.network

import com.google.gson.JsonObject
import com.myproject.myapplication.BuildConfig
import com.myproject.myapplication.inward.AddProduct
import com.myproject.myapplication.model.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


interface WebServiceProvider {

    companion object {


        private const val BASE_URL = "http://15.206.255.26:8080/" //production


        private val okHttpClient = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectionPool(ConnectionPool(3, 60, TimeUnit.SECONDS))
                .retryOnConnectionFailure(true)
                .addInterceptor(HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE })
                .build()

        var retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(
                        RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build()


        private val okHttpClientForMedia = OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .connectionPool(ConnectionPool(3, 120, TimeUnit.SECONDS))
                .retryOnConnectionFailure(true)
                .addInterceptor(HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE })
                .build()
        var retrofitForMedia = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClientForMedia)
                .addCallAdapterFactory(
                        RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        fun getRetrofit(url:String):WebServiceProvider{
            var statusParams = Retrofit.Builder()
                    .baseUrl(url)
                    .client(okHttpClient)
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return statusParams.create(WebServiceProvider::class.java)

        }



    }


    @POST("api/login")
    fun login(@Body data: JsonObject): Single<LoginResponseBean>



    @POST("api/product/search")
    fun productSearch(@Body data: JsonObject): Single<ProductDetailResponse>


    @GET("api/product/search/{storeId}")
    fun productSearchbyCategory(@Path("storeId") storeId: String): Single<CategoryResponseBean>


    @POST("api/add/inventory")
    fun productAdd(@Body data:MutableList<AddProduct?>): Single<ProductDetailResponse>


    @POST("api/update/inventory")
    fun productOut(@Body data:MutableList<AddProduct?>): Single<ProductDetailResponse>


    @GET("api/product/{storeId}")
    fun getInventory(@Path("storeId") storeId: String): Single<StoreInvResponseBean>



    @POST("api/profitDashBoard")
    fun getProfictDetails(@Body data: JsonObject): Single<ProfitResponseBean>




    @POST("api/business/info")
    fun getProfictDetailsByType(@Body data: JsonObject): Single<ProfitDetailsResponseBean>



    @POST("api/product/custom/add")
    fun addCustomeProduct(@Body data: JsonObject): Single<BaseResponse>


    @GET("/api/negative/products/{storeId}")
    fun getapproveproduct(@Path("storeId") storeId: String): Single<ApproveResponse>

    @POST("/api/approve/products")
    fun approvedProducts(@Body data: JsonObject):Single<BaseResponse>


    @POST("/api/product/price")
    fun getProductPrice(@Body data: JsonObject): Single<ProductPriceResponseBean>



    @GET("api/order/export/pdf/11")
    fun pdfGenerator(): Single<ResponseBody>

    @POST("/api/add/store")
    fun addcustomerstore(@Body data: JsonObject):Single<BaseResponse>


    @POST("api/search/variant")
    fun productSearchbyName(@Body data: JsonObject): Single<ProductDetailResponse>




}


