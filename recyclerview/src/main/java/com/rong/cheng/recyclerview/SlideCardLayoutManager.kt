package com.rong.cheng.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author: frc
 * @description:
 * @date:  2021/6/11 6:36 下午
 *
 */
public class SlideCardLayoutManager : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {

        //ViewHolder回收复用
        detachAndScrapAttachedViews(recycler!!)

    }
}