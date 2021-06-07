package com.rong.cheng.advancedui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlin.math.max

/**
 * @author: frc
 * @description:流式布局
 * @date:  2021/6/7 3:48 下午
 *
 */
class FFlowLayout : ViewGroup {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )


    private val mLinesViews = arrayListOf<ArrayList<View>>()
    private val mLinesHeight = arrayListOf<Int>()


    private fun resetMeasure(){
        mLinesViews.clear()
        mLinesHeight.clear()
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        resetMeasure()
        var flexLayoutMeasuredWidth = 0
        var flexLayoutMeasuredHeight = 0
        val selfWidth = MeasureSpec.getSize(widthMeasureSpec)

        var currentLineViews = arrayListOf<View>()
        var currentLineMaxHeight = 0
        var currentLineMaxWidth = 0
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView == null || childView.visibility == GONE) {
                continue
            }

            //measure child view
            val layoutParams = childView.layoutParams
            val childMeasureSpecWidth = getChildMeasureSpec(
                widthMeasureSpec,
                paddingLeft + paddingRight,
                layoutParams.width
            )

            val childMeasureSpecHeight = getChildMeasureSpec(
                heightMeasureSpec,
                paddingTop + paddingBottom,
                layoutParams.height
            )

            childView.measure(childMeasureSpecWidth, childMeasureSpecHeight)

            //get Measured Size
            val measuredWidth = childView.measuredWidth
            val measuredHeight = childView.measuredHeight
            if (currentLineMaxWidth + measuredWidth > selfWidth) {
                //换行
                mLinesViews.add(currentLineViews)
                mLinesHeight.add(currentLineMaxHeight)
                flexLayoutMeasuredHeight += currentLineMaxHeight
                flexLayoutMeasuredWidth = max(flexLayoutMeasuredWidth, currentLineMaxWidth)

                currentLineViews = arrayListOf()
                currentLineMaxWidth = 0
                currentLineMaxHeight = 0

            }

            currentLineViews.add(childView)
            currentLineMaxWidth += measuredWidth
            currentLineMaxHeight = max(currentLineMaxHeight, measuredHeight)
            if (i == childCount - 1) {
                mLinesViews.add(currentLineViews)
                mLinesHeight.add(currentLineMaxHeight)
                flexLayoutMeasuredHeight += currentLineMaxHeight
                flexLayoutMeasuredWidth = max(flexLayoutMeasuredWidth, currentLineMaxWidth)

            }

        }


        val selfWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val selfHeightSize = MeasureSpec.getSize(heightMeasureSpec)
        val selfHeightMode = MeasureSpec.getMode(heightMeasureSpec)
        if (selfWidthMode == MeasureSpec.EXACTLY) {
            flexLayoutMeasuredWidth = selfWidth
        }
        if (selfHeightMode == MeasureSpec.EXACTLY) {
            flexLayoutMeasuredHeight = selfHeightSize
        }


        setMeasuredDimension(flexLayoutMeasuredWidth, flexLayoutMeasuredHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var curL = paddingLeft
        var curT = paddingTop

        for (i in 0 until mLinesViews.size) {
            val currentLineViews = mLinesViews[i]

            for (j in 0 until currentLineViews.size) {
                val childView = currentLineViews[j]
                val left = curL
                val top = curT
                val right = left + childView.measuredWidth
                val bottom = top + childView.measuredHeight
                childView.layout(left, top, right, bottom)
                curL = right
            }
            val currentLineHeight = mLinesHeight[i]
            curL = paddingLeft
            curT += currentLineHeight
        }

    }


}