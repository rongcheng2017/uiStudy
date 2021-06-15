package com.rong.cheng.photoview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * @author: frc
 * @description: 多点操作 actionMasked
 *
 * 1. 计算偏移值
 *      - 记录上一次的偏移值
 * 2. 去等多指中 index和id的概念
 *      - index是变化的
 *      - id是不变的，id是连续的，从0开始加
 * 3. 通过id拿index,通过index拿id
 *      - event.findPointerIndex
 *      - event.getPointerId(actionIndex)
 *
 * @date:  2021/6/15 5:10 下午
 *
 */
class MultiTouchEvent : View {
    //bitmap 默认宽度，后续会缩放
    private val IMAGE_WIDTH = Utils.dpToPixel(300f)
    private lateinit var mPaint: Paint
    private var bitmap: Bitmap? = null
    private var downX = 0f
    private var downY = 0f

    private var offsetX = 0f
    private var offsetY = 0f

    private var lastOffsetX = 0f
    private var lastOffsetY = 0f

    private var currentPointId = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        bitmap = getBitmap(context, attrs)
        if (bitmap == null) return
        init()
    }

    private fun init() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(bitmap!!, offsetX, offsetY, mPaint)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            //Down只触发一次
            MotionEvent.ACTION_DOWN -> {
                //1.
                downX = event.x
                downY = event.y
                lastOffsetX = offsetX
                lastOffsetY = offsetY
                currentPointId = 0

            }
            //move操作的是后按下的手指
            MotionEvent.ACTION_MOVE -> {
                //返回是index==0的坐标
//                offsetX = lastOffsetX + event.x - downX
//                offsetY = lastOffsetY + event.y - downY
                val index = event.findPointerIndex(currentPointId)
                offsetX = lastOffsetX + event.getX(index) - downX
                offsetY = lastOffsetY + event.getY(index) - downY
                invalidate()
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                //获取当前按下的点的index
                val actionIndex = event.actionIndex
                currentPointId = event.getPointerId(actionIndex)

                //解决添加手指时跳跃的问题
                downX = event.getX(actionIndex)
                downY = event.getY(actionIndex)
                lastOffsetX = offsetX
                lastOffsetY = offsetY

            }
            MotionEvent.ACTION_POINTER_UP -> {
                var upIndex = event.actionIndex
                val pointerId = event.getPointerId(upIndex)
                //非活跃手指的抬起不用处理
                if (pointerId == currentPointId) {
                    if (upIndex == event.pointerCount - 1) {
                        upIndex = event.pointerCount - 2
                    } else {
                        upIndex++
                    }
                }
                currentPointId = event.getPointerId(upIndex)
                //解决抬起手指时跳跃的问题
                downX = event.getX(upIndex)
                downY = event.getY(upIndex)
                lastOffsetX = offsetX
                lastOffsetY = offsetY

            }


        }


        return true
    }

    private fun getBitmap(
        context: Context,
        attrs: AttributeSet?
    ): Bitmap? {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.MultiTouchEvent)
        val drawableResId: Int = typedArray.getResourceId(R.styleable.MultiTouchEvent_img, -1)
        bitmap = Utils.getPhoto(resources, drawableResId, IMAGE_WIDTH.toInt())
        typedArray.recycle()
        return bitmap
    }
}