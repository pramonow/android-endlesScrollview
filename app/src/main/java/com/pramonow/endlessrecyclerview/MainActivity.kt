package com.pramonow.endlessrecyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var sampleAdapter = SampleAdapter()
        sampleAdapter.setData(generateString())

        /*
            Endless scoll view declaration block
         */
        var endlessScrollView = findViewById<EndlessScrollView>(R.id.endless_list)
        endlessScrollView.recyclerView.adapter = sampleAdapter

        endlessScrollView.setEndlessScrollCallback(object : EndlessScrollCallback {
            override fun loadMore() {

                var list = sampleAdapter.adapterList + generateString()
                var mutable = list.toMutableList()

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

    fun generateString():MutableList<String>
    {
        var stringList = ArrayList<String>()

        stringList.add("1")
        stringList.add("1")
        stringList.add("1")
        stringList.add("1")
        stringList.add("1")
        stringList.add("1")
        stringList.add("1")
        stringList.add("1")
        stringList.add("1")
        stringList.add("1")

        return stringList
    }
}
