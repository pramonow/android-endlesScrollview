# Android Endless Scroll View for Recycler View [![](https://jitpack.io/v/pramonow/android-endlessScrollview.svg)](https://jitpack.io/#pramonow/android-endlessScrollview)

Implementing endless recycler view the easy way with this library. This library doesn't extends Recycler view, instead it extends towards Nested Scroll View. Implemented fully with Kotlin.

![alt text](https://raw.githubusercontent.com/pramonow/just_images/master/endlessrv.gif)

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
Dependency

	dependencies {
	        implementation 'com.github.pramonow:android-endlessScrollview:-SNAPSHOT'
	}

# How to use

In your xml layout file put in this block

    <com.pramonow.endlessrecyclerview.EndlessScrollView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/endless_scroll_view">
    </com.pramonow.endlessrecyclerview.EndlessScrollView>

For the Android Activity

        //Simply build the view this way
        var endlessScrollView = findViewById<EndlessScrollView>(R.id.endless_list)

        //Put the adapter inside recycler view like usual recycler view
        endlessScrollView.recyclerView.adapter = sampleAdapter

        //Set callback for loading more
        endlessScrollView.setEndlessScrollCallback(object : EndlessScrollCallback {

            //This function will load more list and add it inside the adapter
            override fun loadMore() {
                //Load more of you data here then update your adapter
            }
        })

Recycler view is publicly accessible so it is possible to customize your recycler view and access your adapter
