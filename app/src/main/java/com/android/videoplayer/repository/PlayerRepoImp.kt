package com.android.videoplayer.repository

import com.android.videoplayer.videoDBUtitls.AppDatabase
import com.android.videoplayer.model.VideoModel

class PlayerRepoImp(private val appDatabase: AppDatabase) : VideoRepository {

    override fun saveVideo(videoModel: VideoModel) {
        return appDatabase.videoDao()?.insertVideo(videoModel)!!
    }

    override fun getVideos(): List<VideoModel> {
        return appDatabase.videoDao()?.loadAllVideo()!!
    }

    override fun deleteVideo(videoModel: VideoModel) {
        appDatabase.videoDao()?.delete(videoModel)
    }

    override fun updateVideo(videoModel: VideoModel) {
        return appDatabase.videoDao()?.updateVideo(videoModel)!!
    }
}