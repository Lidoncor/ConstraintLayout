package com.eltex.androidschool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hello = getString(R.string.hello_eltex)
        println(hello)

        val density = resources.displayMetrics.densityDpi
        println(density)
    }
}