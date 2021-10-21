package br.com.gmfonseca.taskmanager.app

import android.app.Application
import br.com.gmfonseca.taskmanager.app.di.taskModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(taskModule())
        }
    }

    override fun onTerminate() {
        super.onTerminate()

        stopKoin()
    }
}
