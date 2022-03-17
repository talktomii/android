package com.furniture.di

import com.furniture.data.apis.WebService
import com.furniture.data.network.Config
import com.furniture.utlis.PrefsManager
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
object NetworkModule {

    @Provides
    @Singleton
    @JvmStatic
    fun okHttpClient(prefsManager: PrefsManager): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .cache(null)
            .addInterceptor(getNetworkInterceptor(prefsManager))
            .addInterceptor(getHttpLoggingInterceptor())
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun retrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Config.BASE_URL_LOCAL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    private fun getNetworkInterceptor(prefsManager: PrefsManager): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
            var token=prefsManager.getString(PrefsManager.PREF_API_TOKEN, "")
            if (token.isNotEmpty()) {
                request.addHeader("Accept", "application/json")
                    .addHeader(
                        "Authorization", "Bearer ${token.replace("\"", "")}"
                    )
                    .addHeader("Language", prefsManager.getDefaultLocale() ?: "en")

            } else
                request.addHeader("Accept", "application/json")
                    .addHeader("Language", prefsManager.getDefaultLocale() ?: "en")
            
            chain.proceed(request.build())
        }
    }


    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    @JvmStatic
    fun webService(retrofit: Retrofit): WebService = retrofit.create(WebService::class.java)
}