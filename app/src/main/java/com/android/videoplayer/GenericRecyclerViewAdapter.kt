package com.android.videoplayer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.android.videoplayer.model.GenericAdapterEntity

class GenericRecyclerViewAdapter<T : GenericAdapterEntity>(var genericAdapterList: List<T>?, var row: Int, var bindingObject: Int) : RecyclerView.Adapter<GenericRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(private var viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root){

        fun bind(obj: Any) {
            viewDataBinding.setVariable(itemView.rootView.id, obj)
            viewDataBinding.executePendingBindings()
        }


    }

    override fun getItemCount(): Int {
        return genericAdapterList?.size!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), row, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val genericModel = genericAdapterList!![position]
        genericModel.setViewPosition(position)
        genericModel.setCount(genericAdapterList!!.size)
        holder.bind(genericModel)
    }

    fun setAdapterList(genericAdapterList: List<T>?) {
        this.genericAdapterList = genericAdapterList
        notifyDataSetChanged()
    }


}