package com.rong.cheng.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        val starList: ArrayList<Star> = arrayListOf()
        mockData(starList)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(TitleItemDecoration(this))
        recyclerView.adapter = ItemAdapter(this, starList)

    }

    private fun mockData(starList: ArrayList<Star>) {
        for (i in 0..3) {
            for (j in 0..19) {
                if (i % 2 == 0) {
                    starList.add(Star("何炅$j", "快乐家族$i"))
                } else {
                    starList.add(Star("汪涵$j", "天天兄弟$i"))
                }
            }
        }
    }
}