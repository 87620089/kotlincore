package com.core.net

import com.core.app.AppConfig
import com.core.app.ConfigKeys
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSession

/**
 * @ProjectName: core2024
 * @Package: com.core.net2
 * @Author: lu
 * @CreateDate: 2024/6/25 15:22
 * @Des:
 */
object  RetrofitClient{
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClientBuilder = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .hostnameVerifier { _: String?, _: SSLSession? -> true }


    private val instance: Retrofit by lazy {
        //获取拦截器
        val interceptors: ArrayList<Interceptor> =
            AppConfig.getConfiguration(ConfigKeys.INTERCEPTOR)
        if (interceptors.isNotEmpty()) {
            for (interceptor in interceptors) {
                okHttpClientBuilder.addInterceptor(interceptor)
            }
        }


        Retrofit.Builder()
            .baseUrl(AppConfig.getConfiguration(ConfigKeys.API_HOST) as String)
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    fun <T> createApi(clazz: Class<T>?): T {
        return instance.create(clazz)
    }
}