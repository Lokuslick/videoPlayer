package com.android.videoplayer.di.module

import android.app.Application
import androidx.room.Room

import com.android.videoplayer.videoDBUtitls.AppDatabase
import com.android.videoplayer.videoDBUtitls.VideoDao
import com.android.videoplayer.repository.PlayerRepoImp
import com.android.videoplayer.repository.VideoRepository
import org.koin.dsl.module

val DatabaseModule = module {

    single { createAppDatabase(get()) }

    single { createVideoDao(get()) }

}

internal fun createAppDatabase(application: Application): AppDatabase {
    return Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        AppDatabase.DATABASE_NAME
    )
        .allowMainThreadQueries()
        .build()
}


fun createVideoDao(appDatabase: AppDatabase): VideoDao? {
    return appDatabase.videoDao()
}


fun createVideoPlayerRepository(appDatabase: AppDatabase): VideoRepository {
    return PlayerRepoImp(appDatabase)
}

