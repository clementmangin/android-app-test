package io.intheloup.apptest

import android.app.Application
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
    }
}