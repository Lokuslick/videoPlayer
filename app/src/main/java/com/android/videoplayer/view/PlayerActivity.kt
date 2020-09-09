package com.android.videoplayer.view

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearSnapHelper
import com.android.videoplayer.R
import com.android.videoplayer.adapter.VideoPlayerAdapter
import com.android.videoplayer.commonUtils.PlayerManager
import com.android.videoplayer.databinding.ActivityPlayerBinding
import com.android.videoplayer.model.VideoModel
import com.android.videoplayer.videoDBUtitls.AppDatabase
import com.android.videoplayer.viewmodel.PlayerViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class PlayerActivity : AppCompatActivity() {

    lateinit var binding: ActivityPlayerBinding
    private val PERMISSION_READ_VIDEO = 53
    private val videoList = arrayListOf<VideoModel>()
    var mAdapter: VideoPlayerAdapter? = null
    private val playerviewModel: PlayerViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player)
        observeLivedata()
        if (checkPermissionRead()) {
            getVideos()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        PlayerManager.getSharedInstance(this)?.pausePlayer()

    }

    private fun getVideos() {
        playerviewModel.getVideoFromDb()
    }

    private fun observeLivedata() {
        playerviewModel.videoListData.observe(this, Observer { video -> setAdapter(video) })
    }

    private fun checkPermissionRead(): Boolean {
        val readExternalStorage: Int =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (readExternalStorage != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_READ_VIDEO
            )
            return false
        }
        return true
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_READ_VIDEO -> {
                if (grantResults.isNotEmpty() && permissions[0] == Manifest.permission.READ_EXTERNAL_STORAGE) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(
                            this,
                            "Please give permission to access this app",
                            Toast.LENGTH_LONG
                        ).show()
                        finish()
                    } else {
                        getVideos()
                    }
                }
            }
        }
    }

    private fun getAllVideoFromGallery() {
        val mCursor: Cursor?
        val columnIndexData: Int
        val columnIndexName: Int
        val columnId: Int
        val columnThumb: Int
        var absolutePathOfFile: String? = null
        val uri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.MediaColumns.DATA,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Thumbnails.DATA
        )
        val orderBy = MediaStore.Images.Media.DATE_TAKEN
        mCursor = applicationContext.contentResolver
            .query(uri, projection, null, null, "$orderBy DESC")
        columnIndexData = mCursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        columnIndexName =
            mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
        columnId = mCursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
        columnThumb = mCursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA)
        while (mCursor.moveToNext()) {
            absolutePathOfFile = mCursor.getString(columnIndexData)
            Log.e("Column", absolutePathOfFile)
            Log.e("Folder", mCursor.getString(columnIndexName))
            Log.e("column_id", mCursor.getString(columnId))
            Log.e("thum", mCursor.getString(columnThumb))
            val mVideo = VideoModel()
            mVideo.isSelected = false
            mVideo.mFilePath = absolutePathOfFile
            mVideo.mVideoThumb = (mCursor.getString(columnThumb))
            playerviewModel.saveVideoData(mVideo)
            videoList.add(mVideo)
        }
        playerviewModel.getVideoFromDb()
    }

    private fun setAdapter(videoList: List<VideoModel>) {
        if (videoList.isEmpty()) {
            getAllVideoFromGallery()
        } else if (mAdapter == null) {
            mAdapter = VideoPlayerAdapter(videoList, this, object : VideoPlayerAdapter.BtnClickListener {
                        override fun onBtnClick(position: Int, mVideo: VideoModel) {
                            playerviewModel.updateVideo(mVideo)
                        }
                    })
            binding.rvVideo.adapter = mAdapter
        } else {
            if (!binding.rvVideo.isComputingLayout) {
                mAdapter!!.setAdapterList(videoList)
            }
        }
        if (binding.rvVideo.onFlingListener == null) {
            val linearSnapHelper = LinearSnapHelper()
            linearSnapHelper.attachToRecyclerView(binding.rvVideo)
        }

    }


}