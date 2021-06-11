package com.rong.cheng.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author: frc
 * @description:
 * @date:  2021/6/11 2:46 下午
 *
 */
public class TitleItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private var textRect: Rect

    //单位dp
    private var headerHeight: Int
    private var headerPaint: Paint
    private var headerTextPaint: Paint
    private var normalDividerPaint: Paint
    private var normalDividerHeight: Int

    init {
        headerHeight = dp2px(context, 120f)
        normalDividerHeight = dp2px(context, 3f)
        headerPaint = Paint()
        headerTextPaint = Paint()
        normalDividerPaint = Paint()
        headerPaint.color = Color.RED
        headerTextPaint.color = Color.WHITE
        normalDividerPaint.color = Color.BLUE

        headerTextPaint.textSize = 50f
        headerTextPaint.isAntiAlias = true
        textRect = Rect()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (parent.adapter is ItemAdapter) {
            val adapter = parent.adapter as ItemAdapter
            val childCount = parent.childCount
            val left: Float = parent.paddingLeft.toFloat()
            val right: Float = parent.width - parent.paddingRight.toFloat()
            for (i in 0 until childCount) {
                val itemView = parent.getChildAt(i)
                val position = parent.getChildLayoutPosition(itemView)
                val isGroupHeader = adapter.isGroupHeader(position)
                /** (itemView.top - headerHeight - parent.paddingTop >= 0) 防止在RecyclerView的Padding区域也绘制了*/
                if (isGroupHeader && itemView.top - headerHeight - parent.paddingTop >= 0) {
                    c.drawRect(
                        left, itemView.top.toFloat() - headerHeight, right,
                        itemView.top.toFloat(), headerPaint
                    )
                    val groupName = adapter.getGroupName(position)
                    headerTextPaint.getTextBounds(groupName, 0, groupName.length, textRect)
                    c.drawText(
                        groupName,
                        left + 20,
                        (itemView.top - headerHeight / 2 + textRect.height() / 2).toFloat(),
                        headerTextPaint
                    )

                } else if (itemView.top - headerHeight - parent.paddingTop >= 0) {
                    //如果不是title
                    //这里的l,t,r,b 是itemView上下左右空出来的区域
                    c.drawRect(
                        left, itemView.top.toFloat() - normalDividerHeight, right,
                        itemView.top.toFloat(), normalDividerPaint
                    )
                }
            }
        }

    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        if (parent.adapter is ItemAdapter) {
            val itemAdapter = parent.adapter as ItemAdapter
            val firstVisibleItemPosition =
                (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            val itemView =
                parent.findViewHolderForLayoutPosition(firstVisibleItemPosition)?.itemView
            val left = parent.paddingLeft.toFloat()
            val right = (parent.width - parent.paddingRight).toFloat()
            val top = parent.paddingTop.toFloat()
            val isGroupName = itemAdapter.isGroupHeader(firstVisibleItemPosition + 1)

            if (isGroupName) {
                //正在减少的Header高度
                val disappearingHeaderHeight =
                    headerHeight.coerceAtMost((itemView?.bottom ?: 0) - parent.paddingTop)

                c.drawRect(left, top, right, top + disappearingHeaderHeight, headerPaint)
                val groupName = itemAdapter.getGroupName(firstVisibleItemPosition)
                headerTextPaint.getTextBounds(groupName, 0, groupName.length, textRect)
                //绘制的文字不能超出区域
                c.clipRect(left,top,right,top+disappearingHeaderHeight)
                c.drawText(
                    groupName,
                    left + 20,
                    top + disappearingHeaderHeight - headerHeight / 2 + textRect.height() / 2,
                    headerTextPaint
                )

            } else {
                c.drawRect(left, top, right, top + headerHeight, headerPaint)
                val groupName = itemAdapter.getGroupName(firstVisibleItemPosition)
                headerTextPaint.getTextBounds(groupName, 0, groupName.length, textRect)
                c.drawText(
                    groupName,
                    left + 20,
                    top + headerHeight / 2 + textRect.height() / 2,
                    headerTextPaint
                )
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.adapter is ItemAdapter) {
            val adapter = parent.adapter as ItemAdapter
            val position = parent.getChildLayoutPosition(view)
            val isGroupHeader = adapter.isGroupHeader(position)
            //如果不是title
            if (isGroupHeader) {


                outRect.set(0, headerHeight, 0, 0)
            } else {
                //这里的l,t,r,b 是itemView上下左右空出来的区域
                outRect.set(0, normalDividerHeight, 0, 0)
            }
        }


    }

    private fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale * 0.5f).toInt()
    }
}