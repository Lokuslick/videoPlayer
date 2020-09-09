package com.android.videoplayer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.videoplayer.BR
import com.android.videoplayer.commonUtils.CallBacks
import com.android.videoplayer.commonUtils.PlayerManager
import com.android.videoplayer.R
import com.android.videoplayer.databinding.AdapterVideoPlayerBinding
import com.android.videoplayer.model.VideoModel

public class VideoPlayerAdapter : RecyclerView.Adapter<VideoPlayerAdapter.PlayerViewHolder> ,
    CallBacks.playerCallBack {

    private var videoList: List<VideoModel>
    private var context: Context? = null
    var mClickListener: BtnClickListener? = null

    constructor(videoList: List<VideoModel>, context: Context?, btnClickListener: BtnClickListener){
        this.videoList = videoList
        this.context = context
        this.mClickListener =btnClickListener
    }


    class PlayerViewHolder(binding: AdapterVideoPlayerBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding: AdapterVideoPlayerBinding = binding
        fun bind(obj: Any?) {
            binding.setVariable(BR.videoModel, obj)
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val binding: AdapterVideoPlayerBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.adapter_video_player, parent, false)
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_video_player, parent, false)
        return PlayerViewHolder(
            binding
        )
    }


    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val mVideo = videoList[position]
        holder.bind(mVideo)
        holder.binding.cbFav.setOnCheckedChangeListener { _cbView, isChecked ->
            if (isChecked) {
                mVideo.isBookMarked =true
                mClickListener?.onBtnClick(position,mVideo)
            }else{
                mVideo.isBookMarked =false
                mClickListener?.onBtnClick(position,mVideo)

            }
        }
        holder.binding.mPlayerView1.player = PlayerManager.getSharedInstance(context!!)?.playerView?.player
        PlayerManager.getSharedInstance(context!!)!!.playStream(mVideo.mFilePath!!)
        PlayerManager.getSharedInstance(context!!)!!.setPlayerListener(this)

    }

    override fun onPlayingEnd() {
        PlayerManager.getSharedInstance(context!!)!!.pausePlayer()

    }

    override fun onItemClickOnItem(albumId: Int?) {

    }

    open interface BtnClickListener {
        fun onBtnClick(position: Int, mVideo: VideoModel)
    }

    fun setAdapterList(videoList: List<VideoModel>
    ) {
        this.videoList = videoList
        notifyDataSetChanged()
    }
}

