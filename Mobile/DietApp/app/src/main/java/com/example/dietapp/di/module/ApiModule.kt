package com.example.dietapp.di.module

import com.example.dietapp.api.AccountService
import com.example.dietapp.api.MealEntriesService
import com.example.dietapp.api.MealsService
import com.example.dietapp.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


@Module
object ApiModule {
    // TODO: REMOVE, TEMPORARY SOLUTION FOR LOCALHOST SERVER
    private fun getUnsafeOkHttpClient(): OkHttpClient {
        try {
            // Create a trust manager that does not validate certificate chains

            val trustAllCerts: Array<TrustManager> = arrayOf(
                object : X509TrustManager {
                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()

                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                }
            )

            // Install the all-trusting trust manager


            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager


            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            @Suppress("DEPRECATION")
            builder.sslSocketFactory(sslSocketFactory)
            builder.hostnameVerifier { _, _ -> true }

            //TODO: Auth interceptor

            return builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideRetrofit(): Retrofit{
        val gson: Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
            .create()
        return Retrofit.Builder()
            .baseUrl(Constants.apiBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory((RxJava2CallAdapterFactory.create()))
            .client(getUnsafeOkHttpClient())
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideAccountService(retrofit: Retrofit): AccountService{
        return retrofit.create(AccountService::class.java)
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideMealService(retrofit: Retrofit): MealsService{
        return retrofit.create(MealsService::class.java)
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideMealEntriesService(retrofit: Retrofit): MealEntriesService{
        return retrofit.create(MealEntriesService::class.java)
    }
}