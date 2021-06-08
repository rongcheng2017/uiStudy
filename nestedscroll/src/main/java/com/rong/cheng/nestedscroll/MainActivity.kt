package com.rong.cheng.nestedscroll

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.rong.cheng.nestedscroll.databinding.ActivityMainBinding
import com.rong.cheng.nestedscroll.recyclerview.RecyclerFragment
import com.rong.cheng.nestedscroll.viewpager.ViewPagerAdapter
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val pagerAdapter =
            ViewPagerAdapter(
                this,
                getPageFragments()
            )
        binding.viewpagerView.adapter = pagerAdapter
        val labels = arrayOf("linear", "scroll", "recycler")
        TabLayoutMediator(
            binding.tabLayout, binding.viewpagerView
        ) { tab, position -> tab.text = labels[position] }.attach()
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.root.postDelayed({
                binding.swipeRefreshLayout.isRefreshing = false
            }, 1000)
        }
    }
    private fun getPageFragments(): List<Fragment>? {
        val data: MutableList<Fragment> = ArrayList()
        data.add(RecyclerFragment())
        data.add(RecyclerFragment())
        data.add(RecyclerFragment())
        return data
    }
}