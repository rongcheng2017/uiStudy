package com.rong.cheng.advancedui

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.google.android.flexbox.FlexboxLayout
import kotlin.math.max

/**
 * @author: frc
 * @description: 自适应布局
 * @date:  2021/6/5 2:56 下午
 *
 */
class FlowLayoutKt : ViewGroup {
    /**
     * 每个Item 水平方向间隔 ，单位px
     */
    private val mHorizontalSpacing = 30

    /**
     * 每个行Item 竖直方向间隔 ，单位px
     */
    private val mVerticalSpacing = 30

    /**
     * 记录所有行的View
     */
    private val mAllLines: ArrayList<ArrayList<View>> = arrayListOf()

    /**
     * 记录每行的高度
     */
    private val mLinesHeight: ArrayList<Int> = arrayListOf()

    private var mItemClickListener: ItemClickListener? = null
    private fun resetMeasureParams() {
        mAllLines.clear()
        mLinesHeight.clear()
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    /**
     * onMeasure()->onLayout()阶段使用MeasuredWith MeasuredHeight
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        /**onMeasure可能会多次调用*/
        resetMeasureParams()
        //当前行的Views，不同行的view 在layout的时候有差异
        var lineViews: ArrayList<View> = arrayListOf()
        //当前行已经使用了的宽
        var lineWidthUsed = 0
        //当前行的高度
        var lineHeight = 0
        //当前FlexLayout的宽,measure过程中FlexLayout的子View要求FlexLayout的宽度
        var flexLayoutWidth = 0
        //当前FlexLayout的高，measure过程中FlexLayout的子View要求FlexLayout的高度
        var flexLayoutHeight = 0

        //父布局给的的宽度
        val selfWidth = MeasureSpec.getSize(widthMeasureSpec)
        //父布局给的高度
        val selfHeight = MeasureSpec.getSize(heightMeasureSpec)

        //遍历子View
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView == null || childView.visibility == GONE) {
                continue
            }

            /**综合父级的MeasureSpec和子View的LayoutParams计算出当前View的MeasureSpec*/
            /**将子View的LayoutParams装换成 MeasureSpec*/
            val childLayoutParams = childView.layoutParams
            val childWithMeasureSpec = getChildMeasureSpec(
                widthMeasureSpec,
                paddingLeft + paddingRight,
                childLayoutParams.width
            )
            // paddingEnd  paddingStart 怎么考虑？得区分 横竖状态吧
            val childHeightMeasureSpec = getChildMeasureSpec(
                heightMeasureSpec,
                paddingTop + paddingBottom,
                childLayoutParams.height
            )
            /**调用子View的measure ，递归调用*/
            childView.measure(childWithMeasureSpec, childHeightMeasureSpec)

            val childMeasuredWith = childView.measuredWidth
            val childMeasuredHeight = childView.measuredHeight
            //新添加一个View后需要的宽度
            if (lineWidthUsed + childMeasuredWith + mHorizontalSpacing > selfWidth) {
                //换行
                flexLayoutWidth = max(flexLayoutWidth, lineWidthUsed + mHorizontalSpacing)
                flexLayoutHeight += lineHeight + mVerticalSpacing
                mLinesHeight.add(lineHeight)
                mAllLines.add(lineViews)
                //清空行数据
                lineViews = arrayListOf()//别用clear()
                lineWidthUsed = 0
                lineHeight = 0

            }
            lineViews.add(childView)
            lineWidthUsed += childMeasuredWith + mHorizontalSpacing
            lineHeight = max(childMeasuredHeight, lineHeight)

            if (i == childCount - 1) {
                //在最后一个子View的时候，需要处理最后一行数据(按上面换行逻辑，最后一行会不被记录)
                flexLayoutWidth = flexLayoutHeight.coerceAtLeast(lineWidthUsed + mHorizontalSpacing)
                flexLayoutHeight += lineHeight + mVerticalSpacing
                mLinesHeight.add(lineHeight)
                mAllLines.add(lineViews)
            }

        }

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        if (widthMode == MeasureSpec.EXACTLY) {
            flexLayoutWidth = selfWidth
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            flexLayoutHeight = selfHeight
        }
        //将当前ViewGroup的宽高设置为自己计算出来的具体值
        setMeasuredDimension(flexLayoutWidth, flexLayoutHeight)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //布局是相对于父级的
        var curL = paddingLeft
        var curT = paddingTop
        val lines = mAllLines.size
        for (i in 0 until lines) {
            val currentLineViews = mAllLines[i]
            for (j in 0 until currentLineViews.size) {
                val currentView = currentLineViews[j]
                val left = curL
                val top = curT
                val right = left + currentView.measuredWidth
                val bottom = top + currentView.measuredHeight
                currentView.layout(left, top, right, bottom)
                curL = right + mHorizontalSpacing
                currentView.setOnClickListener {
                    mItemClickListener?.itemClick(currentView, i, j)
                }
            }
            //下一行的原始left和top
            val lineHeight = mLinesHeight[i]
            curT += lineHeight + mVerticalSpacing
            curL = paddingLeft
        }
    }

    /**
     * onDraw阶段可以使用getWith()和getHeight()
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }


    public fun setOnItemClickListener(listener: ItemClickListener) {
        this.mItemClickListener = listener;
    }

    public interface ItemClickListener {
        fun itemClick(view: View, row: Int, column: Int)
    }
}