package com.example.paintapp

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.paintapp.PaintView.Companion.currentBrushColor
import com.example.paintapp.PaintView.Companion.currentBrushNum
import com.example.paintapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    companion object{
        var path = Path()
        var paintBrush = Paint()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Main layout formation
        supportActionBar?.hide()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        //Color palette on and off
        mainBinding.paletteButton.setOnClickListener {
            if (mainBinding.colorBar.isVisible){
                mainBinding.colorBar.visibility = View.GONE
            }
            else {
                mainBinding.colorBar.visibility = View.VISIBLE
            }
        }

        //Change brush color
        mainBinding.blackColor.setOnClickListener {
            paintBrush.color = Color.BLACK
            currentColor(paintBrush.color)
            Toast.makeText(this,"Black color selected",Toast.LENGTH_SHORT).show()
        }
        mainBinding.redColor.setOnClickListener {
            paintBrush.color = getResources().getColor(R.color.my_red)
            currentColor(paintBrush.color)
            Toast.makeText(this,"Red color selected",Toast.LENGTH_SHORT).show()
        }
        mainBinding.blueColor.setOnClickListener {
            paintBrush.color = getResources().getColor(R.color.my_blue)
            currentColor(paintBrush.color)
            Toast.makeText(this,"Blue color selected",Toast.LENGTH_SHORT).show()
        }
        mainBinding.greenColor.setOnClickListener {
            paintBrush.color = getResources().getColor(R.color.my_green)
            currentColor(paintBrush.color)
            Toast.makeText(this,"Green color selected",Toast.LENGTH_SHORT).show()
        }


        //Change brush styles
        mainBinding.pencilButton.setOnClickListener {
            currentBrushNum = 1
            Toast.makeText(this,"Pencil selected",Toast.LENGTH_SHORT).show()
        }
        mainBinding.arrowButton.setOnClickListener {
            currentBrushNum = 2
            Toast.makeText(this,"Arrow selected",Toast.LENGTH_SHORT).show()
        }
        mainBinding.rectangleButton.setOnClickListener {
            currentBrushNum = 3
            Toast.makeText(this,"Rectangle selected",Toast.LENGTH_SHORT).show()
        }
        mainBinding.circleButton.setOnClickListener {
            currentBrushNum = 4
            Toast.makeText(this,"Circle selected",Toast.LENGTH_SHORT).show()
        }

        /*
        //For clear button
        pathList.clear()
        colorList.clear()
        path.reset()
        //add all other lists nd in companion object
        */

    }

    private fun currentColor(color :Int){
        currentBrushColor = color
        path = Path() //create new path
    }

}