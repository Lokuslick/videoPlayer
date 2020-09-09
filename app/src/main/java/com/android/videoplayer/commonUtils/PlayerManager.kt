package com.android.videoplayer.commonUtils

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.*
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.*

class PlayerManager private constructor(mContext: Context) {
    var playerView: PlayerView
    var dataSourceFactory: DefaultDataSourceFactory
    var uriString = ""
    var mPlayList: ArrayList<String>? = null
    var playlistIndex = 0
    var listner: CallBacks.playerCallBack? = null
    private val mPlayer: SimpleExoPlayer?
    fun setPlayerListener(mPlayerCallBack: CallBacks.playerCallBack?) {
        listner = mPlayerCallBack
    }

    fun playStream(urlToPlay: String) {
        uriString = urlToPlay
        var mp4VideoUri = Uri.parse(uriString)
        val videoSource: MediaSource?
        if (uriString.toUpperCase().contains("M3U8")) {
            videoSource = HlsMediaSource.Factory(dataSourceFactory)
                .setAllowChunklessPreparation(true)
                .createMediaSource(mp4VideoUri, null, null)
        } else {
            mp4VideoUri = Uri.parse(urlToPlay)
            videoSource = ExtractorMediaSource.Factory(dataSourceFactory)
                .setExtractorsFactory(DefaultExtractorsFactory()).createMediaSource(mp4VideoUri)
        }


        if (mPlayer != null && videoSource != null) {
            mPlayer.prepare(videoSource)
            mPlayer.playWhenReady = true
        }
    }

    fun pausePlayer() {
        if (mPlayer != null) {
            mPlayer.playWhenReady = false
            mPlayer.playbackState
        }
    }

    fun resumePlayer() {
        if (mPlayer != null) {
            mPlayer.playWhenReady = true
            mPlayer.playbackState
        }
    }

    val isPlayerPlaying: Boolean
        get() = mPlayer!!.playWhenReady

    fun readURLs(url: String?): ArrayList<String>? {
        if (url == null) return null
        val allURls = ArrayList<String>()
        return try {
            val urls = URL(url)
            val `in` = BufferedReader(
                InputStreamReader(
                    urls
                        .openStream()
                )
            )
            var str: String
            while (`in`.readLine().also { str = it } != null) {
                allURls.add(str)
            }
            `in`.close()
            allURls
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    companion object {

        private val BANDWIDTH_METER = DefaultBandwidthMeter()
        private const val TAG = "ExoPlayerManager"
        private var mInstance: PlayerManager? = null

        fun getSharedInstance(mContext: Context): PlayerManager? {
            if (mInstance == null) {
                mInstance =
                    PlayerManager(mContext)
            }
            return mInstance
        }

    }

    init {
        val videoTrackSelectionFactory: TrackSelection.Factory = AdaptiveTrackSelection.Factory(
            BANDWIDTH_METER
        )
        val trackSelector: TrackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        mPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector)
        playerView = PlayerView(mContext)
        playerView.useController = true
        playerView.requestFocus()
        playerView.setPlayer(mPlayer)
        val mp4VideoUri = Uri.parse(uriString)
        dataSourceFactory = DefaultDataSourceFactory(
            mContext,
            Util.getUserAgent(mContext, "videoPlayerTrell"),
            BANDWIDTH_METER
        )
        val videoSource: MediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
            .createMediaSource(mp4VideoUri)
        mPlayer.prepare(videoSource)
        mPlayer.addListener(object : Player.EventListener {
            override fun onTimelineChanged(
                timeline: Timeline,
                manifest: Any?,
                reason: Int
            ) {
                Log.i(TAG, "onTimelineChanged: ")
            }

            override fun onTracksChanged(
                trackGroups: TrackGroupArray,
                trackSelections: TrackSelectionArray
            ) {
                Log.i(TAG, "onTracksChanged: ")
            }

            override fun onLoadingChanged(isLoading: Boolean) {
                Log.i(TAG, "onLoadingChanged: ")
            }

            override fun onPlayerStateChanged(
                playWhenReady: Boolean,
                playbackState: Int
            ) {
                Log.i(TAG, "onPlayerStateChanged: ")
                if (playbackState == 4 && mPlayList != null && playlistIndex + 1 < mPlayList!!.size) {
                    Log.e(TAG, "Video Changed...")
                    playlistIndex++
                    listner?.onItemClickOnItem(playlistIndex)
                    playStream(mPlayList!![playlistIndex])
                } else if (playbackState == 4 && mPlayList != null && playlistIndex + 1 == mPlayList!!.size) {
                    mPlayer.playWhenReady = false
                }
                if (playbackState == 4 && listner != null) {
                    listner!!.onPlayingEnd()
                }
            }

            override fun onRepeatModeChanged(repeatMode: Int) {
                Log.i(TAG, "onRepeatModeChanged: ")
            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
                Log.i(TAG, "onShuffleModeEnabledChanged: ")
            }

            override fun onPlayerError(error: ExoPlaybackException) {
                Log.i(TAG, "onPlayerError: ")
            }

            override fun onPositionDiscontinuity(reason: Int) {
                Log.i(TAG, "onPositionDiscontinuity: ")
            }

            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
                Log.i(TAG, "onPlaybackParametersChanged: ")
            }

            override fun onSeekProcessed() {
                Log.i(TAG, "onSeekProcessed: ")
            }
        })
    }
}
