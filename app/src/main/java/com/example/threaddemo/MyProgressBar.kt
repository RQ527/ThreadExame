package com.example.threaddemo

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/3/7
 */
class MyProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr:Int = 0):View(context, attrs, defStyleAttr) {
    private var paint:Paint?=null
    var mWidth = 1
    var mHeight = 1
    var progress:Float = 0f
    set(value) {
        field=value
        invalidate()
    }
    init {
        paint = Paint()
        paint?.apply {
            color = Color.BLUE
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = 3f
            isAntiAlias =true
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = getMySize(100,widthMeasureSpec)
        mHeight=getMySize(100,heightMeasureSpec)
        setMeasuredDimension(mWidth, mHeight)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
          canvas?.drawRect(0f,(mHeight.toFloat()/2)-50,mWidth.toFloat(),
              (mHeight.toFloat()/2)+50,Paint().apply {
              style=Paint.Style.FILL_AND_STROKE
              isAntiAlias = true
              color = Color.GRAY
          })
        if (progress>mWidth){
            progress=mWidth.toFloat()
        }
        paint?.let { canvas?.drawRect(0f,(mHeight.toFloat()/2)-50,progress,
            (mHeight.toFloat()/2)+50,it) }
    }


    /**
     * 获取xml布局里的width和mHeight
     *
     * @param defaultSize 默认大小
     * @param measureSpec 解析的测量码
     * @return
     */
    private fun getMySize(defaultSize: Int, measureSpec: Int): Int {
        var mySize = defaultSize
        val mode = View.MeasureSpec.getMode(measureSpec) //测量模式
        val size = View.MeasureSpec.getSize(measureSpec) //测量尺寸
        when (mode) {
            MeasureSpec.UNSPECIFIED -> mySize = defaultSize
            MeasureSpec.AT_MOST, MeasureSpec.EXACTLY -> mySize = size
        }
        return mySize
    }
}