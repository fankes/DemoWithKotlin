package com.highcapable.demowithkotlin.kotlindemo

import android.util.Log
import android.widget.TextView

class KotlinDemoClass {

    fun changeText(view: TextView) {
        // Kotlin code was changing Will run Incremental compilation.

        val demoText = "Kotlin code was changing Will run Incremental compilation"

        println("change will run in incremental compilation")
        println("change will run in incremental compilation")
        println("change will run in incremental compilation")

        println("change will run in incremental compilation")

        println("change will run in incremental compilation")

        println("test")

        Log.d("test", "test")

        view.text = "Text was changed->$demoText"
    }
}