package com.rong.cheng.photoview

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller

/**
 * @author: frc
 * @description:
 *  1. 加载图片
 *  2. 图片居中
 *  3. 计算最大最小缩放比
 *  4. 双击放大图片，双击缩小图片：GestureDetector
 *          - GestureDetector接管Touch事件
 *          - 添加缩放动画
 *  5. 滑动处理
 *          - 其实就是偏移 需要计算offsetX,offsetY
 *          - 滑动边界处理
 *          - 惯性滑动
 *              - OverScroller
 *              - postOnAnimation()
 *  6. 双指缩放
 *          - ScaleGestureDetector
 *          - 缩放因子
 *          - 接管TouchEvent,双指缩放优先
 *
 *  7. 点击哪放大哪
 *          - 以点击区域为中心进行放大
 *
 *  8. 不放大状态下支持缩放
 *          - onScale()中isEnlarge==true
 *
 *  9. 双指缩放边界
 *
 * @date:  2021/6/15 2:18 下午
 *
 */
class PhotoView : View {
    //bitmap 默认宽度，后续会缩放
    private val IMAGE_WIDTH = Utils.dpToPixel(300f)

    private var bitmap: Bitmap? = null
    private lateinit var mPaint: Paint

    //bitmap原始偏移量
    private var originalOffsetX: Float = 0f
    private var originalOffsetY: Float = 0f

    //bitmap滑动时的偏移量
    private var offsetX: Float = 0f
    private var offsetY: Float = 0f

    //bitmap最小方向适配当前View大小的缩放比例，如果宽图，则bitmap高填充满当前View的高
    private var bigScale: Float = 1f

    //最大方向适配当前View大小的缩放比例，如果宽图，则bitmap宽填充满当前View的宽
    private var smallScale: Float = 1f


    private var mCurrentScale: Float = 1f

    //超过边界的范围比例
    private val OVER_SCALE_FACTOR = 1.5f

    private lateinit var mGestureDetector: GestureDetector

    //标记当前bitmap是否被放大
    private var isEnlarge: Boolean = false
    private var overScroller: OverScroller? = null
    private var scaleGestureDetector: ScaleGestureDetector? = null

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
        mGestureDetector = GestureDetector(context, PhotoGestureDetector())
        overScroller = OverScroller(context)
        scaleGestureDetector = ScaleGestureDetector(context, PhotoScaleGestureListener())
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        bitmap?.let {
            //缩放偏移量
            val scaleFaction = (mCurrentScale - smallScale) / (bigScale - smallScale)
            canvas.translate(offsetX * scaleFaction, offsetY * scaleFaction)

            canvas.scale(mCurrentScale, mCurrentScale, width / 2f, height / 2f)

            canvas.drawBitmap(it, originalOffsetX, originalOffsetY, mPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //双指缩放优先
        var result = scaleGestureDetector?.onTouchEvent(event)
        if (scaleGestureDetector?.isInProgress != true) {
            //接管Touch事件
            result = mGestureDetector.onTouchEvent(event)
        }
        return result ?: false
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //图片居中显示
        originalOffsetX = (width - bitmap!!.width) / 2f
        originalOffsetY = (height - bitmap!!.height) / 2f

        bitmap?.let {
            if ((it.width / it.height).toFloat() > (width / height).toFloat()) {
                //宽图
                smallScale = width / it.width.toFloat()
                bigScale = height / it.height.toFloat() * OVER_SCALE_FACTOR
            } else {
                smallScale = height / it.height.toFloat()
                bigScale = width / it.width.toFloat() * OVER_SCALE_FACTOR
            }
            mCurrentScale = smallScale
        }

    }

    /**控制偏移量的取值范围*/
    private fun fixOffsets() {
        bitmap?.let {
            //最小
            offsetX = offsetX.coerceAtMost((it.width * bigScale - width) / 2)
            //最大
            offsetX = offsetX.coerceAtLeast(-(it.width * bigScale - width) / 2)
            offsetY = offsetY.coerceAtMost((it.height * bigScale - height) / 2)
            offsetY = offsetY.coerceAtLeast(-(it.height * bigScale - height) / 2)
        }

    }

    private fun getBitmap(
        context: Context,
        attrs: AttributeSet?
    ): Bitmap? {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.PhotoView)
        val drawableResId: Int = typedArray.getResourceId(R.styleable.PhotoView_photo, -1)
        bitmap = Utils.getPhoto(resources, drawableResId, IMAGE_WIDTH.toInt())
        typedArray.recycle()
        return bitmap
    }

    inner class PhotoGestureDetector : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            // true 接管
            return true
        }

        override fun onShowPress(e: MotionEvent?) {
            super.onShowPress(e)
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return super.onSingleTapUp(e)
        }

        /**
         * 滑动事件
         * @param [e1] 手指按下
         * @param [e2]当前的
         * @param [distanceX] 旧位置-新位置
         */
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (isEnlarge) {
                //bitmap放大时才能滑动
                offsetX -= distanceX
                offsetY -= distanceY
                fixOffsets()
                invalidate()
            }
            return super.onScroll(e1, e2, distanceX, distanceY)
        }

        override fun onLongPress(e: MotionEvent?) {
            super.onLongPress(e)
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (isEnlarge) {
                overScroller?.fling(
                    offsetX.toInt(), offsetY.toInt(), velocityX.toInt(), velocityY.toInt(),
                    (-(bitmap!!.width * bigScale - width) / 2).toInt(),
                    ((bitmap!!.width * bigScale - width) / 2).toInt(),
                    (-(bitmap!!.height * bigScale - height) / 2).toInt(),
                    ((bitmap!!.height * bigScale - height) / 2).toInt(),
                    300, 300
                )
                //下一帧动画的时候执行，不用while 节省资源
                postOnAnimation(FlingRunner())
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            return super.onSingleTapConfirmed(e)
        }


        override fun onDoubleTap(e: MotionEvent): Boolean {
            isEnlarge = !isEnlarge
            if (isEnlarge) {
                //放大
                //7.
                //点击处到图片中心的偏移量是多少
                offsetX = (e.x - width / 2f) - (e.x - width / 2f) * bigScale / smallScale
                offsetY = (e.y - height / 2f) - (e.y - height / 2) * bigScale / smallScale
                fixOffsets()
                getScaleAnimation()?.start()
            } else {
                //缩小
                getScaleAnimation()?.reverse()
            }
            return super.onDoubleTap(e)
        }

        override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
            return super.onDoubleTapEvent(e)
        }


    }


    private var scaleAnimator: ObjectAnimator? = null

    //给放大缩小添加动画
    private fun getScaleAnimation(): ObjectAnimator? {
        if (scaleAnimator == null) {
            scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", 0f)
        }
        scaleAnimator?.setFloatValues(smallScale, bigScale)
        return scaleAnimator
    }

    public fun setCurrentScale(scale: Float) {
        this.mCurrentScale = scale
        invalidate()
    }

    inner class FlingRunner : Runnable {
        override fun run() {
            overScroller?.let {
                //动画是否完成
                if (it.computeScrollOffset()) {
                    offsetX = it.currX.toFloat()
                    offsetY = it.currY.toFloat()
                    invalidate()
                    postOnAnimation(this)
                }
            }
        }

    }

    inner class PhotoScaleGestureListener : ScaleGestureDetector.OnScaleGestureListener {

        var initScale: Float = 1f
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            if ((mCurrentScale > smallScale && !isEnlarge) || (mCurrentScale == smallScale && !isEnlarge)) {
                //8.
                isEnlarge = !isEnlarge
            }
            //缩放因子
            mCurrentScale = initScale * detector.scaleFactor
            fixScale()
            invalidate()
            return false
        }

        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            initScale = mCurrentScale
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {
        }
    }

    /**控制双指缩放边界*/
    private fun fixScale() {
        //9.
        mCurrentScale = mCurrentScale.coerceAtMost(bigScale * OVER_SCALE_FACTOR)
        mCurrentScale = mCurrentScale.coerceAtLeast(smallScale / OVER_SCALE_FACTOR)
    }
}