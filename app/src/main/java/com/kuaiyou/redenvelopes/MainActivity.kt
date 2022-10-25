package com.kuaiyou.redenvelopes


import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import com.kuaiyou.redenvelopes.manager.RainManager
import com.kuaiyou.redenvelopes.widget.RainLayout

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
        rainManager.rain()
    }
}