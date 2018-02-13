package io.intheloup.apptest.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.facebook.drawee.view.SimpleDraweeView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.intheloup.apptest.App
import io.intheloup.apptest.R
import io.intheloup.apptest.model.Place
import io.intheloup.apptest.network.mapper.SearchMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotterknife.bindView
import timber.log.Timber
import java.util.concurrent.TimeUnit


class SearchActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    val searchText: EditText by bindView(R.id.search_text)
    val searchList: RecyclerView by bindView(R.id.search_list)
    private var adapter: SearchAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        layoutManager = LinearLayoutManager(this)
        searchList.setLayoutManager(layoutManager)

        adapter = SearchAdapter() {
            val intent = Intent(this, DetailActivity::class.java).apply {
//                putExtra(EXTRA_MESSAGE, message)
            }
            startActivity(intent)
        }
        searchList.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        // just an example of how to use the search API
        // you are not required to use RX, feel free to use the classic callback implementation
        // just remember to call `searchCallback()` rather than `search`
        disposable = RxTextView.textChanges(searchText)
                .throttleLast(300, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .filter(String::isNotBlank)
                .doOnNext{text -> Timber.d(text)}
                .flatMap { text ->
                    App.dependencies.api.search.search(text)
                            .subscribeOn(Schedulers.io())
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    if (response.isSuccessful && response.body() != null) {
                        // simple mapper to convert the json payload into clean model
                        val places = SearchMapper.map(response.body()!!)
                        places.forEach { Timber.d("place: ${it.name}") }
                        adapter?.items = places
                    }
                }
    }

    override fun onStop() {
        disposable?.dispose()
        super.onStop()
    }

    inner class SearchAdapter(val listener: (Place) -> Unit): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

        var items: List<Place> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

        inner class ViewHolder(// each data item is just a string in this case
                var view: View) : RecyclerView.ViewHolder(view) {

            val imageView: SimpleDraweeView
            val nameView: TextView
            val categoryView: TextView

            init {
                imageView = view.findViewById(R.id.item_thumbnail)
                nameView = view.findViewById(R.id.item_name)
                categoryView = view.findViewById(R.id.item_category)
            }

            fun bind(item: Place, listener: (Place) -> Unit) = with(itemView) {
                imageView.setImageURI(item.image)
                nameView.text = item.name
                categoryView.text = item.category
                setOnClickListener { listener(item) }
            }
        }

        override fun getItemCount(): Int = items.count()

        override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], listener)

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_cell, null))
    }
}
