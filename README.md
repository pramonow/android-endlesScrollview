# Android Endless Scroll View for Recycler View [![](https://jitpack.io/v/pramonow/android-endlessrecyclerview.svg)](https://jitpack.io/#pramonow/android-endlessrecyclerview)

Implementing endless recycler view the easy way with this library. This library doesn't extends Recycler view, instead it extends towards Frame Layout. Implemented fully with Kotlin.

![alt text](https://raw.githubusercontent.com/pramonow/just_images/master/endlessrv.gif)

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
Dependency

	dependencies {
	        implementation 'com.github.pramonow:android-endlessrecyclerview:-SNAPSHOT'
	}

# How to use

In your xml layout file put in this block

    <com.pramonow.endlessrecyclerview.EndlessRecyclerView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/endless_scroll_view">
    </com.pramonow.endlessrecyclerview.EndlessRecyclerView>

For the Android Activity

        //Simply build the view this way
        var endlessRecyclerView = findViewById<EndlessRecyclerView>(R.id.endless_list)

        //Put the adapter inside recycler view like usual recycler view
        endlessRecyclerView.adapter = sampleAdapter

        //Set callback for loading more
        endlessRecyclerView.setEndlessScrollCallback(object : EndlessScrollCallback {

            //This function will load more list and add it inside the adapter
            override fun loadMore() {
                //Load more of you data here then update your adapter
            }
        })

Recycler view is publicly accessible so it is possible to customize your recycler view and access your adapter

Several Methods that can be used:
- fun setLastPage() => Do this when you don't want to load data anymore
- fun blockLoading() => Block load more from being called, usually used when waiting for API call to finish
- fun releaseBlock() => Unblock load more, usaually used when API call has finished
- fun setLoadBeforeBottom(boolean: Boolean) => Used to set whether you want to load data before user reach bottom 

