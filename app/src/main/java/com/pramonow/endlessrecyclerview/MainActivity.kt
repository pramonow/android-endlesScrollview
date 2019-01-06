package com.pramonow.endlessrecyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

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
        var endlessRecylerView = findViewById<EndlessRecyclerView>(R.id.endless_list)

        //Put the adapter inside recycler view like usual recycler view
        endlessRecylerView.adapter = sampleAdapter

        // Uncomment to allow load before scroll to most bottom
        // Currently will start loading list when second last item is on screen
        //endlessRecylerView.setLoadBeforeBottom(true)

        // Also, offset for load before bottom can be set, default is 2 object before bottom
        //endlessRecylerView.loadOffset = 5

        //Set callback for loading more
        endlessRecylerView.setEndlessScrollCallback(object : EndlessScrollCallback {

            //This function will load more list and add it inside the adapter
            override fun loadMore() {

                Log.d("baniman", "load")
                //Now list view can be set so that it will block load until certain task finish
                endlessRecylerView.blockLoading()

                var list = sampleAdapter.adapterList + generateString()
                var mutable = list.toMutableList()

                //Using thread and sleep to make the load more visible
                Thread(object : Runnable {
                    override fun run() {
                        Thread.sleep(500)
                        runOnUiThread(object : Runnable {
                            override fun run() {
                                sampleAdapter.setData(mutable)
                                sampleAdapter.notifyDataSetChanged()

                                //list view will release the blocking action here, when all data is fetched
                                endlessRecylerView.releaseBlock()
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
