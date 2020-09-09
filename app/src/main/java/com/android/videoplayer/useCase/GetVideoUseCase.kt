package com.android.videoplayer.useCase

import com.android.videoplayer.model.VideoModel
import com.android.videoplayer.repository.VideoRepository

class GetVideoUseCase(private val videoRepository: VideoRepository) {
    fun getVideos(): List<VideoModel> {
        return videoRepository.getVideos()
    }
}
