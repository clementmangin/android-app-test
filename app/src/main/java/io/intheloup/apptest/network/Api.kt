package io.intheloup.apptest.network

import com.squareup.moshi.Moshi
import io.intheloup.apptest.network.api.SearchApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class Api(private val okHttpClient: OkHttpClient,
          private val moshi: Moshi) {

    val search: SearchApi

    init {
        val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.bim-booking.com/api/v4/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        search = retrofit.create(SearchApi::class.java)
    }
}