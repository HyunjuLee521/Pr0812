package com.example.pr0812.domain.repository

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object NetworkManager {
    private const val REST_API_KEY = "e2b464b10fa12fed34f03692cf19e50c"
    private const val BASE_URL = "https://dapi.kakao.com/"

    private val retrofit =
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkhttp())
            .baseUrl(BASE_URL)
            .build()

    private fun getOkhttp(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val headerInterceptor = Interceptor {
            val request = it.request()
                .newBuilder()
                .header("Authorization", "KakaoAK $REST_API_KEY")
                .build()
            return@Interceptor it.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    val searchBookApi: SearchBookApi = retrofit.create(SearchBookApi::class.java)


}