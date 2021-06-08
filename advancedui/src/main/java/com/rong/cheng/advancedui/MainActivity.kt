package com.rong.cheng.advancedui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.NestedScrollingParent
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        findViewById<FlowLayoutKt>(R.id.flow_layout).setOnItemClickListener(object :
//            FlowLayoutKt.ItemClickListener {
//            override fun itemClick(view: View, row: Int, column: Int) {
//                Log.e("Frc","row: $row , column :$column")
//            }
//        })
    }
}