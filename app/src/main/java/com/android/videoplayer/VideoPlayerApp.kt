package com.android.videoplayer

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.android.videoplayer.di.module.AppModule
import com.android.videoplayer.di.module.DatabaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class VideoPlayerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        startKoin {
            androidLogger()
            androidContext(this@VideoPlayerApp)
            modules(listOf(
                DatabaseModule,
                AppModule
            ))
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}
