package com.android.videoplayer.model

open class GenericAdapterEntity{

    private var viewPosition : Int = 0

    private var count = 0

    fun getViewPosition(): Int {
        return viewPosition
    }

    fun setViewPosition(viewPosition: Int) {
        this.viewPosition = viewPosition
    }

    fun getCount(): Int {
        return count
    }

    fun setCount(count: Int) {
        this.count = count
    }

}