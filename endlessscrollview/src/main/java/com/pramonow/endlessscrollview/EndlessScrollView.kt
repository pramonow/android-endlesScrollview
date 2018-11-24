package com.pramonow.endlessrecyclerview

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import com.pramonow.endlessscrollview.R

class EndlessScrollView : NestedScrollView {

    private var loadingView:View
    private var lastPage:Boolean = false
    private var blockLoad:Boolean = false
    private var loadBeforeBottom = false

    //Callback provided for endless scroll
    private lateinit var endlessScrollCallback:EndlessScrollCallback

    //publicly accessible recycler view
    public var recyclerView:RecyclerView

    constructor(context:Context, attributeSet: AttributeSet) : super(context,attributeSet) {

        //Set the view
        View.inflate(context, R.layout.endless_scoll_view_layout,this)
        this.recyclerView = findViewById(R.id.recycler_view)
        this.loadingView = findViewById(R.id.loading_box)
        recyclerView.isNestedScrollingEnabled = false

        //Set default layout for recycler view to be linear
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    @Deprecated("Recycler view can be directly accessed")
    public fun setRecyclerViewLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        recyclerView.layoutManager = layoutManager
    }

    //Set callback for the endless scroll view, this function must be called else the endless scroll view will not work
    public fun setEndlessScrollCallback(endlessScrollCallback: EndlessScrollCallback) {
        this.endlessScrollCallback = endlessScrollCallback

        //block to test for super lazy load
        val layoutManager = recyclerView.getLayoutManager() as LinearLayoutManager
        val pos = layoutManager.findLastCompletelyVisibleItemPosition()
        val numItems = recyclerView.getAdapter()?.getItemCount()
        //return pos >= numItems

        setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {

            //Set callback when user is scrolling
            override fun onScrollChange(v:NestedScrollView,scrollX:Int,scrollY:Int,oldScrollX:Int,oldScrollY:Int) {

                /*
                    When the scroll view has reached the end then do the according:
                    - if not last page then call the load more
                    - if last page then stop loading anymore data
                 */
                if(scrollY == ( v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    if (lastPage)
                        loadingView.visibility = View.INVISIBLE
                    else if(blockLoad == false){
                        endlessScrollCallback.loadMore()
                        loadingView.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    //Do this when you don't want to load data anymore
    public fun setLastPage()
    {
        lastPage = true
        loadingView.visibility = View.INVISIBLE
    }

    public fun blockLoading()
    {
        blockLoad = true
    }

    public fun releaseBlock()
    {
        blockLoad = false
    }
}