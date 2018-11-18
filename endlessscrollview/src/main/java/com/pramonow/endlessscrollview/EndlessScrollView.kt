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

    //private lateinit var adapter: Adapter<RecyclerView.ViewHolder>
    private lateinit var endlessScrollCallback:EndlessScrollCallback


    public var recyclerView:RecyclerView

    constructor(context:Context, attributeSet: AttributeSet) : super(context,attributeSet) {

        View.inflate(context, R.layout.view_lib,this)
        //this.recyclerView = RecyclerView(context)
        this.recyclerView = findViewById(R.id.recycler_view)
        recyclerView.isNestedScrollingEnabled = false

        //Automatically set it linear
        setRecyclerViewLayoutManager(LinearLayoutManager(context))

        this.loadingView = findViewById(R.id.loading_box)
    }

    /*public fun setRecyclerViewAdapter(adapter:Adapter<RecyclerView.ViewHolder>)
    {
        this.adapter = adapter
    }*/

    public fun setRecyclerViewLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        recyclerView.layoutManager = layoutManager
    }

    public fun setEndlessScrollCallback(endlessScrollCallback: EndlessScrollCallback) {
        this.endlessScrollCallback = endlessScrollCallback

        setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {

            override fun onScrollChange(v:NestedScrollView,scrollX:Int,scrollY:Int,oldScrollX:Int,oldScrollY:Int) {

                if(scrollY == ( v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    if (lastPage)
                        loadingView.visibility = View.INVISIBLE
                    else {
                        endlessScrollCallback.loadMore()
                        loadingView.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    public fun setLastPage()
    {
        lastPage = true
        loadingView.visibility = View.INVISIBLE
    }


}