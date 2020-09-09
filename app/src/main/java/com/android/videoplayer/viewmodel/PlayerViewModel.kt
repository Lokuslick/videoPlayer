package com.android.videoplayer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.videoplayer.model.VideoModel
import com.android.videoplayer.useCase.DeleteVideoUseCase
import com.android.videoplayer.useCase.GetVideoUseCase
import com.android.videoplayer.useCase.SaveVideoUseCase
import com.android.videoplayer.useCase.UpdateVideoUseCase

class PlayerViewModel(
    private val saveVideoUseCase: SaveVideoUseCase,
    private val getVideoUseCase: GetVideoUseCase,
    private val deleteVideoUseCase: DeleteVideoUseCase,
   private val updateVideoUseCase: UpdateVideoUseCase) : ViewModel() {


     val videoListData = MutableLiveData<List<VideoModel>>()

    fun saveVideoData(videoModel: VideoModel) {
        saveVideoUseCase.saveVideoItem(videoModel)
    }

    fun getVideoFromDb() {
        videoListData.value = getVideoUseCase.getVideos()
    }
    fun updateVideo(videoModel: VideoModel) {
        updateVideoUseCase.updateVideoItem(videoModel)
        val list = videoListData.value as ArrayList<VideoModel>
        list[videoModel.id] = videoModel
        videoListData.value = list
    }

}
