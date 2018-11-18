package com.pramonow.endlessrecyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SampleAdapter: RecyclerView.Adapter<SampleAdapter.SampleVH>(){

    var adapterList:MutableList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleVH {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.dummy_adapter,parent,false)

        return SampleVH(itemView)
    }

    override fun getItemCount(): Int {
        return adapterList.size;
    }

    fun setData(promoList: MutableList<String>)
    {
        this.adapterList = promoList
    }

    override fun onBindViewHolder(holder: SampleVH, position: Int) {

    }

    class SampleVH : RecyclerView.ViewHolder {

        constructor(itemView: View) : super(itemView){

        }


    }
}