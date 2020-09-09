package com.android.videoplayer.repository

import com.android.videoplayer.model.VideoModel

interface VideoRepository {

    fun saveVideo(videoModel: VideoModel)

    fun getVideos(): List<VideoModel>

    fun deleteVideo(videoModel: VideoModel)

    fun updateVideo(videoModel: VideoModel)

}