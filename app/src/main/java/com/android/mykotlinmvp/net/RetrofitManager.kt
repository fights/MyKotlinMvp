package com.android.mykotlinmvp.net

import com.android.mykotlinmvp.MyApplication
import com.android.mykotlinmvp.utils.AppUtil
import com.android.mykotlinmvp.utils.Constants
import com.android.mykotlinmvp.utils.NetUtil
import com.orhanobut.logger.Logger
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by zhangguanjun on 2018/1/8.
 */
object RetrofitManager{

    private var client: OkHttpClient? = null
    private var retrofit: Retrofit? = null
    private val maxStale = 60*60*24*2

    val apiService: ApiService by lazy { getRetrofit()!!.create(ApiService::class.java) }

    private fun getRetrofit(): Retrofit? {
        if (retrofit == null) {
            synchronized(RetrofitManager::class.java) {
                if (retrofit == null) {
                    //日志拦截器
                    val httpLoggingInterceptor = HttpLoggingInterceptor()
                    //设置日志打印的等级
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                    //设置缓存位置和大小
                    val cacheFile = File(MyApplication.context.cacheDir, "my_kotlin_cache")
                    val cache = Cache(cacheFile, 1024 * 1024 * 50)

                    client = OkHttpClient.Builder()
                            .addInterceptor(httpLoggingInterceptor)
                            .addInterceptor(cacheInterceptor)
                            .addNetworkInterceptor(cacheInterceptor)
                            .addInterceptor(queryParameterInterceptor)
                            .cache(cache)
                            .connectTimeout(1000,TimeUnit.MILLISECONDS)
                            .readTimeout(1000,TimeUnit.MILLISECONDS)
                            .writeTimeout(1000,TimeUnit.MILLISECONDS)
                            .build()

                    //Retrofit实例
                    retrofit = Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .client(client!!)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                }
            }
        }
        return retrofit
    }

    private val cacheInterceptor = Interceptor {
        chain ->
        var request = chain.request()
        if (!NetUtil.isNetConnected()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()

            Logger.e("have no network")
        }
        var response = chain.proceed(request)
        if (NetUtil.isNetConnected()) {
            val requestCache = request.cacheControl().toString()
            response.newBuilder()
                    .header("Cache-Control",requestCache)
                    .removeHeader("Pragma")
                    .build()
        }else{
            response.newBuilder()
                    .addHeader("Cache-Control","public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build()
        }

        response
    }

    private val queryParameterInterceptor = Interceptor {
        chain ->
        var request = chain.request()
        val modifiedUrl = request.url().newBuilder()
                .addQueryParameter("phoneSystem", "android")
                .addQueryParameter("phoneModel", AppUtil.getPhoneModel())
                .build()
        request = request.newBuilder()
                .url(modifiedUrl)
                .build()
        val response = chain.proceed(request)
        response
    }

}