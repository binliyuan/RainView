package com.kuaiyou.rainview


import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import com.kuaiyou.rainview.manager.RainManager
import com.kuaiyou.rainview.manager.RainManager.RainInterface
import com.kuaiyou.rainview.widget.RainLayout
import com.kuaiyou.rainview.R

class MainActivity : Activity() {
    var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var rootView = window.decorView
        var RainLayout = RainLayout(this)
        Log.d(TAG, "onCreate: " + rootView.layoutParams)
        RainLayout.layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
        (rootView as ViewGroup).addView(RainLayout)
        val rainManager = RainManager(this, RainLayout, 0)
        val callback = CallBack()
        rainManager.setRainCallback(callback)
        rainManager.rain()
    }

    class CallBack : RainInterface {
        override fun onRainStart() {
            TODO("Not yet implemented")
        }

        override fun onRaining() {
            TODO("Not yet implemented")
        }

        override fun onRainClick() {
            Log.d("CallBack", "onRainClick: ")
        }

        override fun onRainEnd() {
            TODO("Not yet implemented")
        }

    }
}