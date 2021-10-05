package com.highcapable.demowithkotlin

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.highcapable.demowithkotlin.kotlindemo.KotlinDemoClass

class MainActivity : AppCompatActivity() {

    // Tips: Run ':app' and change those code to see the result.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Find the viewId
        val textView = findViewById<TextView>(R.id.only_change_this_id_kotlin_will_rebuilding)
        // Test those code.
        KotlinDemoClass().changeText(textView)
    }
}