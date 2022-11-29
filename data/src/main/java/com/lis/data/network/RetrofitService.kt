package com.lis.data.network

import com.lis.domain.models.CompanyListModel
import com.lis.domain.models.CompanyModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("test.php")
    suspend fun getCompanyList(): Response<CompanyListModel>

    @GET("test.php?")
    suspend fun getCompanyData(@Query("id") id: String): Response<CompanyModel>

    companion object {
        private const val BASE_URL = "https://lifehack.studio/test_task/"

        fun create(): RetrofitService {
            val okHttpClient = OkHttpClient().newBuilder()
                .retryOnConnectionFailure(true)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitService::class.java)
        }
    }
}