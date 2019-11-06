package com.slice.verifly.customview

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.widget.ImageView
import com.slice.verifly.R
import android.graphics.RectF
import android.view.MotionEvent
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

class CircularImageView(context: Context, attrs: AttributeSet?): ImageView(context, attrs) {

    constructor(context: Context): this(context, null)

    companion object {
        const val DEF_PRESS_HIGHLIGHT_COLOR = 0x32000000
    }

    private var mBitmap: Bitmap? = null
    private var mBitmapShader: Shader? = null

    private var mShaderMatrix: Matrix
    private var mBitmapPaint: Paint
    private var mStrokePaint: Paint
    private var mPressedPaint: Paint
    private var mStrokeBounds: RectF
    private var mBitmapDrawBounds: RectF
    private var mHighlightEnable: Boolean
    private var mInitialized: Boolean

    private var mPressed: Boolean = false

    init {
        var strokeColor = Color.TRANSPARENT
        var strokeWidth = 0f
        var highlightEnable = true
        var highlightColor = DEF_PRESS_HIGHLIGHT_COLOR
        attrs?.let {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircularImageView, 0, 0)
            strokeColor = a.getColor(R.styleable.CircularImageView_strokeColor, Color.TRANSPARENT)
            strokeWidth = a.getDimensionPixelSize(R.styleable.CircularImageView_strokeWidth, 0).toFloat()
            highlightEnable = a.getBoolean(R.styleable.CircularImageView_highlightEnable, true)
            highlightColor = a.getColor(R.styleable.CircularImageView_highlightColor, DEF_PRESS_HIGHLIGHT_COLOR)
            a.recycle()
        }
        mShaderMatrix = Matrix()
        mBitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = strokeColor
            style = Paint.Style.STROKE
            this.strokeWidth = strokeWidth
        }
        mPressedPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = highlightColor
            style = Paint.Style.FILL
        }
        mStrokeBounds = RectF()
        mBitmapDrawBounds = RectF()
        mHighlightEnable = highlightEnable
        mInitialized = true

        setupBitmap()
    }

    private fun setupBitmap() {
        if (!mInitialized) {
            return
        }
        mBitmap = getBitmapFromDrawable(drawable)
        mBitmap?.let {
            mBitmapShader = BitmapShader(it, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            mBitmapPaint.shader = mBitmapShader
        } ?: return
        updateBitmapSize()
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        drawable?.let {
            return if (it is BitmapDrawable) {
                it.bitmap
            } else {
                val bitmap = Bitmap.createBitmap(
                    it.intrinsicWidth,
                    it.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                it.apply {
                    bounds = Rect(0, 0, canvas.width, canvas.height)
                    draw(canvas)
                }
                bitmap
            }
        } ?: return null
    }

    private fun updateBitmapSize() {
        if (mBitmap == null) return;
        mBitmap?.let { bitmap ->
            val dx: Float
            val dy: Float
            val scale: Float

            // scale up/down with respect to this view size and maintain aspect ratio
            // translate bitmap position with dx/dy to the center of the image
            if (bitmap.width < bitmap.height) {
                scale = mBitmapDrawBounds.width() / bitmap.width
                dx = mBitmapDrawBounds.left
                dy = mBitmapDrawBounds.top - (bitmap.height * scale / 2f) + (mBitmapDrawBounds.width() / 2f)
            } else {
                scale = mBitmapDrawBounds.height() / bitmap.height
                dx = mBitmapDrawBounds.left - (bitmap.width * scale / 2f) + (mBitmapDrawBounds.width() / 2f)
                dy = mBitmapDrawBounds.top
            }
            mShaderMatrix.setScale(scale, scale)
            mShaderMatrix.postTranslate(dx, dy)
            mBitmapShader?.setLocalMatrix(mShaderMatrix)
        } ?: return
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        setupBitmap()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        setupBitmap()
    }

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        setupBitmap()
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        setupBitmap()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val halfStrokeWidth = mStrokePaint.strokeWidth / 2f
        updateCircleDrawBounds(mBitmapDrawBounds)
        mStrokeBounds.set(mBitmapDrawBounds)
        mStrokeBounds.inset(halfStrokeWidth, halfStrokeWidth)
        updateBitmapSize()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            outlineProvider = CircleImageViewOutlineProvider(mStrokeBounds)
        }
    }

    private fun updateCircleDrawBounds(bounds: RectF) {
        val contentWidth = (width - paddingLeft - paddingRight).toFloat()
        val contentHeight = (height - paddingTop - paddingBottom).toFloat()

        var left = paddingLeft.toFloat()
        var top = paddingTop.toFloat()
        if (contentWidth > contentHeight) {
            left += (contentWidth - contentHeight) / 2f
        } else {
            top += (contentHeight - contentWidth) / 2f
        }

        val diameter = min(contentWidth, contentHeight)
        bounds.set(left, top, left + diameter, top + diameter)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var processed = false
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!isInCircle(event.x, event.y)) {
                    return false
                }
                processed = true
                mPressed = true
                invalidate()
            }

            MotionEvent.ACTION_CANCEL -> {

            }

            MotionEvent.ACTION_UP -> {
                processed = true
                mPressed = false
                invalidate()
                if (!isInCircle(event.x, event.y)) {
                    return false
                }
            }
        }
        return super.onTouchEvent(event) or processed
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            drawBitmap(it)
            drawStroke(it)
            drawHighlight(it)
        }
    }

    private fun isHighlightEnable(): Boolean {
        return mHighlightEnable
    }

    private fun setHighlightEnable(enable: Boolean) {
        mHighlightEnable = enable
        invalidate()
    }

    @ColorInt
    private fun getHighlightColor(): Int {
        return mPressedPaint.color
    }

    private fun setHighlightColor(@ColorInt color: Int) {
        mPressedPaint.color = color
        invalidate()
    }

    @ColorInt
    private fun getStrokeColor(): Int {
        return mStrokePaint.color
    }

    private fun setStrokeColor(@ColorInt color: Int) {
        mStrokePaint.color = color
        invalidate()
    }

    @Dimension
    private fun getStrokeWidth(): Float {
        return mStrokePaint.strokeWidth
    }

    private fun setStrokeWidth(@Dimension width: Float) {
        mStrokePaint.strokeWidth = width
        invalidate()
    }

    private fun drawBitmap(canvas: Canvas) {
        canvas.drawOval(mBitmapDrawBounds, mBitmapPaint)
    }

    private fun drawStroke(canvas: Canvas) {
        if (mStrokePaint.strokeWidth > 0f) {
            canvas.drawOval(mStrokeBounds, mStrokePaint)
        }
    }

    private fun drawHighlight(canvas: Canvas) {
        if (mHighlightEnable and mPressed) {
            canvas.drawOval(mBitmapDrawBounds, mPressedPaint)
        }
    }

    private fun isInCircle(x: Float, y: Float): Boolean {
        val distance = sqrt(x = (mBitmapDrawBounds.centerX() - x).toDouble().pow(2) + (mBitmapDrawBounds.centerY() - y).toDouble().pow(2))
        return distance <= (mBitmapDrawBounds.width()/2)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    class CircleImageViewOutlineProvider(rect: RectF): ViewOutlineProvider() {

        private var mRect: Rect? = null

        init {
            mRect = Rect(
                rect.left.toInt(),
                rect.top.toInt(),
                rect.right.toInt(),
                rect.bottom.toInt()
            )
        }

        override fun getOutline(view: View?, outline: Outline?) {
            mRect?.let { outline?.setOval(it) }
        }
    }
}