package io.intheloup.apptest

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import io.intheloup.apptest.network.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class Dependencies {

    val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    val api: Api = Api(okHttpClient, moshi)
}