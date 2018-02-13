package io.intheloup.apptest.network.api

import io.intheloup.apptest.network.json.SearchJson
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("search")
    fun search(@Query("q") search: String): Observable<Response<SearchJson>>

    @GET("search")
    fun searchCallback(@Query("q") search: String): Call<Response<SearchJson>>
}