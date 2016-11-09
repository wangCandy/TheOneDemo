package com.wly.onedemo.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.wly.onedemo.R
import org.jetbrains.anko.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAcnkoContentView()
    }

    fun setAcnkoContentView(){
        relativeLayout(){
            textView(){
                text = "测试"
                textSize = 12f
            }
        }
    }
}
