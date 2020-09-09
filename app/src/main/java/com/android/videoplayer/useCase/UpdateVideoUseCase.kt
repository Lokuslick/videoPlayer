package com.android.videoplayer.useCase

import com.android.videoplayer.model.VideoModel
import com.android.videoplayer.repository.VideoRepository

class UpdateVideoUseCase(private val videoRepository: VideoRepository) {
    fun updateVideoItem(videoModel: VideoModel) {
        videoRepository.updateVideo(videoModel)
    }
}