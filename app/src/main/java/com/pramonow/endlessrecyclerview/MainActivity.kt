package com.pramonow.endlessrecyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Set adapter to be
        var sampleAdapter = SampleAdapter()
        sampleAdapter.setData(generateString())

        /*
            Endless scroll view declaration block
         */
        var endlessScrollView = findViewById<EndlessScrollView>(R.id.endless_list)

        //Put the adapter inside recycler view like usual recycler view
        endlessScrollView.recyclerView.adapter = sampleAdapter

        //Set callback for loading more
        endlessScrollView.setEndlessScrollCallback(object : EndlessScrollCallback {

            //This function will load more list and add it inside the adapter
            override fun loadMore() {

                var list = sampleAdapter.adapterList + generateString()
                var mutable = list.toMutableList()

                //Using thread and sleep to make the load more visible
                Thread(object : Runnable {
                    override fun run() {
                        Thread.sleep(300)
                        runOnUiThread(object : Runnable {
                            override fun run() {
                                sampleAdapter.setData(mutable)
                                sampleAdapter.notifyDataSetChanged()
                            }
                        })
                    }
                }).start()
            }
        })
    }

    //Generate default data for the adapter
    fun generateString():MutableList<String>
    {
        var stringList = ArrayList<String>()

        for(i in 1..10)
            stringList.add("Sample")

        return stringList
    }
}
