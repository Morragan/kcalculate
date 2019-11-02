package com.example.dietapp.di.module

import android.content.SharedPreferences
import com.example.dietapp.api.AccountService
import com.example.dietapp.api.SocialService
import com.example.dietapp.api.MealEntriesService
import com.example.dietapp.api.MealsService
import com.example.dietapp.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
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

    private fun getInterceptor(sharedPreferences: SharedPreferences) = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val requestUrl = request.url()
            val requestMethod = request.method()
            val requestPath = requestUrl.encodedPath()

            if (requestPath.contains("/login") && requestMethod == "post" ||
                requestPath.contains("/register") && requestMethod == "post" ||
                requestPath.contains("/refresh-token") && requestMethod == "post"
            ) {
                return chain.proceed(request)
            }

            val accessToken =
                sharedPreferences.getString(Constants.sharedPreferencesKeyAccessToken, "")!!

            val newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .url(requestUrl)
                .build()

            return chain.proceed(newRequest)
        }
    }

    // TODO: REMOVE, TEMPORARY SOLUTION FOR LOCALHOST SERVER
    private fun getUnsafeOkHttpClient(sharedPreferences: SharedPreferences): OkHttpClient {
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

            builder.addInterceptor(getInterceptor(sharedPreferences))

            return builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideRetrofit(sharedPreferences: SharedPreferences): Retrofit {
        val gson: Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
            .create()
        return Retrofit.Builder()
            .baseUrl(Constants.apiBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory((RxJava2CallAdapterFactory.create()))
            .client(getUnsafeOkHttpClient(sharedPreferences))
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideAccountService(retrofit: Retrofit): AccountService {
        return retrofit.create(AccountService::class.java)
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideMealService(retrofit: Retrofit): MealsService {
        return retrofit.create(MealsService::class.java)
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideMealEntriesService(retrofit: Retrofit): MealEntriesService {
        return retrofit.create(MealEntriesService::class.java)
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideFriendsService(retrofit: Retrofit): SocialService {
        return retrofit.create(SocialService::class.java)
    }
}