package com.android.videoplayer.useCase

import com.android.videoplayer.model.VideoModel
import com.android.videoplayer.repository.VideoRepository

class SaveVideoUseCase(private val videoRepository: VideoRepository) {

    fun saveVideoItem(videoModel: VideoModel) {
        videoRepository.saveVideo(videoModel)
    }
}
