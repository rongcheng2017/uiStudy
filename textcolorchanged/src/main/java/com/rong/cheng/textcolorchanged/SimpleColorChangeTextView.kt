package com.rong.cheng.textcolorchanged

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * @author: frc
 * @description:
 * 1. 文字x轴居中
 *      - TextAlign
 *      - paint.measureText
 * 2. 计算baseLine
 *      - canvas.drawText(text,width/2-textWidth/2,baseLine,mPaint)
 *
 * @date:  2021/6/15 6:15 下午
 *
 */
class SimpleColorChangeTextView : AppCompatTextView {
    private var mPaint: Paint = Paint()
    private val text = "何丽丽"
    private val strokeWidth = 3f

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCenterLineX(canvas)
        drawCenterLineY(canvas)


        //Y轴 baseLine
        mPaint.textSize = 100f
        mPaint.measureText(text)
        val fontMetrics = mPaint.fontMetrics
        val top = fontMetrics.top
        val ascent = fontMetrics.ascent
        val descent = fontMetrics.descent
        val bottom = fontMetrics.bottom
        val leading = fontMetrics.leading
        // height/2 -((ascent+descent)/2-descent) = height/2 -(ascent+descent)/2
        val baseLine = height / 2f - (ascent + descent) / 2

        drawTextLeft(baseLine, canvas)
        drawTextRight(baseLine, canvas)

        drawXLine(canvas, baseLine + top)
        drawXLine(canvas, baseLine + ascent)
        drawXLine(canvas, baseLine + descent)
        drawXLine(canvas, baseLine + bottom)
        drawXLine(canvas, baseLine + leading)
    }

    private var textPercent: Float = 0.0f
    private fun drawTextRight(baseLine: Float, canvas: Canvas) {
        canvas.save()
        //X轴 TextAlign
        mPaint.textSize = 100f
        //文字水平居中
        //val textWidth = mPaint.measureText(text)
        //canvas.drawText(text,width/2-textWidth/2,baseLine,mPaint)
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.BLUE
        mPaint.textAlign = Paint.Align.LEFT
        mPaint.isAntiAlias = true
        val textWidth = mPaint.measureText(text)
        val left = width / 2 - textWidth / 2 + textWidth * textPercent
        var right = width / 2 + textWidth / 2
        val rect = Rect(left.toInt(), 0, right.toInt(), height)
        canvas.clipRect(rect)
        canvas.drawText(text, width / 2 - textWidth / 2, baseLine, mPaint)

        canvas.restore()

    }

    private fun drawTextLeft(baseLine: Float, canvas: Canvas) {
        canvas.save()
        //X轴 TextAlign
        mPaint.textSize = 100f
        //文字水平居中
        //val textWidth = mPaint.measureText(text)
        //canvas.drawText(text,width/2-textWidth/2,baseLine,mPaint)
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.RED
        mPaint.textAlign = Paint.Align.LEFT
        mPaint.isAntiAlias = true
        val textWidth = mPaint.measureText(text)
        val left = width / 2 - textWidth / 2
        var right = left + textWidth * textPercent
        val rect = Rect(left.toInt(), 0, right.toInt(), height)
        canvas.clipRect(rect)
        canvas.drawText(text, left, baseLine, mPaint)

        canvas.restore()

    }

    private fun drawXLine(canvas: Canvas, height: Float) {
        mPaint.color = Color.GRAY
        mPaint.strokeWidth = 1f
        mPaint.style = Paint.Style.STROKE
        canvas.drawLine(0f, height, width.toFloat(), height, mPaint)

    }

    private fun drawCenterLineY(canvas: Canvas) {
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.RED
        mPaint.strokeWidth = 3f
        canvas.drawLine(
            (width / 2).toFloat(),
            0f,
            (width / 2 - strokeWidth / 2),
            height.toFloat(),
            mPaint
        )
    }

    private fun drawCenterLineX(canvas: Canvas) {
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.RED
        mPaint.strokeWidth = 3f
        canvas.drawLine(
            0f,
            (height / 2).toFloat(),
            width.toFloat(),
            (height / 2 - strokeWidth / 2),
            mPaint
        )
    }

    fun setTextPercent(percent: Float) {
        this.textPercent = percent
        invalidate()
    }
}