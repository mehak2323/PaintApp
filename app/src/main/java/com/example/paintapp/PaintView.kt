package com.example.paintapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.paintapp.MainActivity.Companion.paintBrush
import com.example.paintapp.MainActivity.Companion.path

class PaintView: View {

    //Variables shared with other class/activity
    companion object {
        var currentBrushColor = Color.BLACK
        var currentBrushNum = 1
    }

    var params: ViewGroup.LayoutParams? = null

    //Storing paths or coordinate list for all items
    //Points is a data class created for holding coordinates
    private var pathList = ArrayList<Path>()
    private var arrowList = ArrayList<Points>()
    private var rectList = ArrayList<Points>()
    private var circleList = ArrayList<Points>()

    //Color list of all items drawn
    private var colorList = ArrayList<Int>()
    private var colorListArrow = ArrayList<Int>()
    private var colorListRect = ArrayList<Int>()
    private var colorListCircle = ArrayList<Int>()

    //Start and end coordinates for shapes
    private var startX = 0f
    private var startY = 0f
    private var endX = 0f
    private var endY = 0f


    //Constructors for custom view, mainly initialize brush
    constructor(context: Context) : this(context, null) {
        initPaintBrush()
    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        initPaintBrush()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initPaintBrush()
    }

    //initializing paint brush
    private fun initPaintBrush() {
        paintBrush.isAntiAlias = true //texture of brush
        paintBrush.color = currentBrushColor  //brush color
        paintBrush.style = Paint.Style.STROKE //Fill or stroke
        paintBrush.strokeJoin = Paint.Join.ROUND //brush ending round
        paintBrush.strokeWidth = 8f //brush width or size

        //size of this custom view
        params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        super.onTouchEvent(event)

        //Current touch coordinates
        val x = event.x
        val y = event.y

        when (event.action) {
            //When we just touched screen
            MotionEvent.ACTION_DOWN -> {
                when (currentBrushNum) {
                    //Pencil
                    1 -> {
                        path.moveTo(x, y)
                    }
                    //Arrow
                    2 -> {
                        startX = x
                        startY = y
                        endX = x
                        endY = y
                    }
                    //Rectangle
                    3 -> {
                        startX = x
                        startY = y
                        endX = x
                        endY = y
                    }
                    //Circle
                    4 -> {
                        startX = x
                        startY = y
                        endX = x
                        endY = y
                    }
                }
                return true
            }
            //When we are dragging our touch
            MotionEvent.ACTION_MOVE-> {
                //Only pencil needs to be drawn while dragging
                //as other shapes will be infinitely created
                if(currentBrushNum==1) {
                    path.lineTo(x, y)
                    pathList.add(path)
                    colorList.add(currentBrushColor)
                    invalidate()
                }
                return true
            }
            //When we lift up finger, now draw the shape
            MotionEvent.ACTION_UP -> {
                when (currentBrushNum) {
                    1 -> { } //No need as already drawn
                    2 -> {
                        endX = x
                        endY = y
                        arrowList.add(Points(startX, startY, endX, endY))
                        colorListArrow.add(currentBrushColor)
                    }
                    3 -> {
                        endX = x
                        endY = y
                        rectList.add(Points(startX, startY, endX, endY))
                        colorListRect.add(currentBrushColor)
                    }
                    4 -> {
                        endX = x
                        endY = y
                        circleList.add(Points(startX, startY, endX, endY))
                        colorListCircle.add(currentBrushColor)
                    }
                }
                invalidate()
                return true
            }
        }
        postInvalidate()
        return false
    }

    //onDraw called when view is created or invalidate() is called
    override fun onDraw(canvas: Canvas) {

        super.onDraw(canvas)

        //Draw all the line paths according to color
        for (i in pathList.indices) {
            paintBrush.setColor(colorList[i])
            canvas.drawPath(pathList[i], paintBrush)
        }

        //Draw all the arrows according to color
        for (ar in arrowList.indices) {
            paintBrush.setColor(colorListArrow[ar])
            canvas.drawLine(
                arrowList[ar].startX,
                arrowList[ar].startY,
                arrowList[ar].endX,
                arrowList[ar].endY,
                paintBrush
            )
        }

        //Draw all the rectangles according to color
        for (rec in rectList.indices) {
            paintBrush.setColor(colorListRect[rec])
            //DrawRect takes left,top,right,bottom values
            canvas.drawRect(
                rectList[rec].startX, rectList[rec].startY,
                rectList[rec].endX, rectList[rec].endY, paintBrush
            )
        }

        //Draw all the circles according to color
        for (cir in circleList.indices) {
            paintBrush.setColor(colorListCircle[cir])
            //DrawOval takes left,top,right,bottom values
            canvas.drawOval(
                circleList[cir].startX, circleList[cir].startY,
                circleList[cir].endX, circleList[cir].endY, paintBrush
            )
        }

        invalidate() //Continuously draws all objects
    }
}
