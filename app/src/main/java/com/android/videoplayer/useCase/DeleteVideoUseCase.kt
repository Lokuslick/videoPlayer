package com.android.videoplayer.useCase

import com.android.videoplayer.model.VideoModel
import com.android.videoplayer.repository.VideoRepository

class DeleteVideoUseCase(private val videoRepository: VideoRepository ) {
    fun deleteVideoItem(videoModel: VideoModel) {
        videoRepository.deleteVideo(videoModel)
    }
}
