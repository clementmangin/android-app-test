package io.intheloup.apptest.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.intheloup.apptest.App
import io.intheloup.apptest.R
import io.intheloup.apptest.network.mapper.SearchMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class SearchActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }

    override fun onStart() {
        super.onStart()

        // just an example of how to use the search API
        // you are not required to use RX, feel free to use the classic callback implementation
        // just remember to call `searchCallback()` rather than `search`
        disposable = App.dependencies.api.search.search("a")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    if (response.isSuccessful && response.body() != null) {
                        // simple mapper to convert the json payload into clean model
                        val places = SearchMapper.map(response.body()!!)
                        places.forEach { Timber.d("place: ${it.name}") }
                    }
                }
    }

    override fun onStop() {
        disposable?.dispose()
        super.onStop()
    }
}
