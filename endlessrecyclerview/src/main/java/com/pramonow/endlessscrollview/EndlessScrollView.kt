package com.pramonow.endlessrecyclerview

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.pramonow.endlessscrollview.R

class EndlessScrollView : FrameLayout {

    private var lastPage:Boolean = false
    private var blockLoad:Boolean = false
    private var loadBeforeBottom = false

    //Offset for load before bottom
    public var loadOffset = 3

    //Callback provided for endless scroll
    private lateinit var endlessScrollCallback:EndlessScrollCallback

    //publicly accessible recycler view
    public var recyclerView:RecyclerView

    constructor(context:Context, attributeSet: AttributeSet) : super(context,attributeSet) {

        //Set the view
        View.inflate(context, R.layout.endless_recycler_view_layout,this)

        this.recyclerView = findViewById(R.id.recycler_view)
        recyclerView.isNestedScrollingEnabled = false

        //Set default layout for recycler view to be linear
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    //Set callback for the endless scroll view, this function must be called else the endless scroll view will not work
    public fun setEndlessScrollCallback(endlessScrollCallback: EndlessScrollCallback) {

        this.endlessScrollCallback = endlessScrollCallback

        if(!loadBeforeBottom)
            setOnMostBottomScrollCallback()
        else
            setLoadBeforeReachingBottomCallback()
    }

    //Do this when you don't want to load data anymore
    fun setLastPage()
    {
        lastPage = true
    }

    //Block load more from being called, usually used when waiting for API call to finish
    fun blockLoading()
    {
        blockLoad = true
    }

    //Unblock load more, usaually used when API call has finished
    fun releaseBlock()
    {
        blockLoad = false
    }

    //For loading data before reaching bottom
    fun setLoadBeforeBottom(boolean: Boolean)
    {
        this.loadBeforeBottom = boolean
    }

    //Callback for loading data when scrolled to most bottom
    private fun setOnMostBottomScrollCallback()
    {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                //block to test for super lazy load
                val layoutManager = recyclerView.getLayoutManager() as LinearLayoutManager
                val pos = layoutManager.findLastCompletelyVisibleItemPosition()
                val numItems = recyclerView.getAdapter()?.getItemCount()

                if (pos >= numItems!! - 1) {
                    if (lastPage){

                    }
                    else if(!blockLoad){
                        endlessScrollCallback.loadMore()
                    }
                }
            }
        })
    }

    //Callback for loading data before reaching most bottom
    private fun setLoadBeforeReachingBottomCallback() {

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                //block to test for super lazy load
                val layoutManager = recyclerView.getLayoutManager() as LinearLayoutManager
                val pos = layoutManager.findLastCompletelyVisibleItemPosition()
                val numItems = recyclerView.getAdapter()?.getItemCount()

                if (pos >= numItems!! - loadOffset) {
                    if (lastPage){

                    }
                    else if(!blockLoad){
                        endlessScrollCallback.loadMore()
                    }
                }
            }
        })
    }
}