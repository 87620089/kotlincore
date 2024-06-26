package com.core.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint

import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.SizeUtils
import com.core.R


/**
 *  @ProjectName: SpeakFreely
 *  @Package: com.eningqu.speakfreely.ui.view
 *  @Author: lu
 *  @CreateDate: 2024/2/26 15:46
 *  @Des: 圆形下载
 */
class CircleDownLoadProgress @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    //背景画笔
    private val paint = Paint()

    //圆环宽度
    private var circleWidth = SizeUtils.dp2px(2F).toFloat()

    private var processWidth = SizeUtils.dp2px(24F)

    //圆形的rect
    private val rectF = RectF()

    //圆环背景
    private var circleBgColor = 0

    //圆环进度
    private var circleColor = 0

    //当前进度
    var currentProcess = 0F

    //最大进度
    private var maxProcess = 100f

    //半径
    private var radius = 0f

    //正方形宽高
    private var centerSquare = 0f

    init {
        val typeArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CircleProgressView, 0, 0
        )

        circleBgColor = typeArray.getColor(
            R.styleable.CircleProgressView_circleBgColor, resources.getColor(
                R.color.line
            )
        )
        circleColor = typeArray.getColor(
            R.styleable.CircleProgressView_circleColor,
            resources.getColor(R.color.default_color)
        )


        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = circleWidth
        paint.strokeCap = Paint.Cap.ROUND
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        processWidth = measuredWidth

        radius = (processWidth / 2).toFloat()
        centerSquare = (processWidth / 3).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //半径
        val circleRadius = radius - circleWidth

        //绘制背景
        paint.color = circleBgColor
        canvas.drawCircle(radius, radius, circleRadius, paint)

        //绘制正方形
        paint.color = circleColor
        paint.style = Paint.Style.FILL
        canvas.drawRect(
            centerSquare, centerSquare,
            (centerSquare * 2), (centerSquare * 2), paint
        )

        //绘制进度
        paint.style = Paint.Style.STROKE
        rectF.set(
            circleWidth,
            circleWidth,
            (processWidth - circleWidth),
            (processWidth - circleWidth)
        )
        canvas.drawArc(rectF, 270F, (currentProcess * 360 / maxProcess), false, paint)
    }
}