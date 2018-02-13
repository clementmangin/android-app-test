package io.intheloup.apptest

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import timber.log.Timber

class App : Application() {

    companion object {
        lateinit var dependencies: Dependencies
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        App.dependencies = Dependencies()
        Fresco.initialize(this)
    }
}