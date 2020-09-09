package com.android.videoplayer.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video")
class VideoModel : GenericAdapterEntity() {

    @PrimaryKey(autoGenerate = true)
    var id = 0

    var videoTitle: String? = null

    var videoDuration: String? = null

    var mFilePath: String? = null

    var mVideoThumb: String? = null

    var isSelected = false

    var isBookMarked = false



}