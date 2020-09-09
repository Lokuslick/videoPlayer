package com.android.videoplayer.videoDBUtitls

import androidx.room.*
import com.android.videoplayer.model.VideoModel


@Dao
interface VideoDao {

    @Query("SELECT * FROM video ORDER BY id")
    fun loadAllVideo(): List<VideoModel>

    @Insert
    fun insertVideo(videoModel: VideoModel)

    @Update
    fun updateVideo(videoModel: VideoModel)

    @Delete
    fun delete(videoModel: VideoModel)

    @Query("SELECT * FROM video WHERE id = :id")
    fun loadvideoById(id: Int): VideoModel?
}