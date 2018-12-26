package com.pramonow.endlessrecyclerview

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.pramonow.endlessrecyclerviewmodule.R

class EndlessRecyclerView : FrameLayout {

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

        //Scroll listener for the recycler view
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val layoutManager = recyclerView.getLayoutManager() as LinearLayoutManager
                val pos = layoutManager.findLastCompletelyVisibleItemPosition()
                val numItems = recyclerView.getAdapter()?.getItemCount()

                loadByPosition(loadBeforeBottom, pos, numItems!!)
            }
        })
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

    //Function for load more data logic
    private fun loadByPosition(loadBeforeBottom : Boolean, position:Int, numberItems:Int) {

        //Initialize default offset
        var offset = 1

        if(loadBeforeBottom == true)
            offset = loadOffset

        if(!blockLoad && !lastPage && position >= numberItems - offset)
            endlessScrollCallback.loadMore()
    }

}