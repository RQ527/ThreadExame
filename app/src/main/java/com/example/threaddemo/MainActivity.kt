package com.example.threaddemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.Toast
import kotlin.math.log

class MainActivity : AppCompatActivity(), View.OnClickListener {
    val TAG = "RQ"
    private var progressBar: MyProgressBar? = null
    private var progressBar2: MyProgressBar? = null
    private var progressBar3: MyProgressBar? = null
    private var progressBar4: MyProgressBar? = null
    private var progressBar5: MyProgressBar? = null
    private var button1: Button? = null
    private var button2: Button? = null
    private var button3: Button? = null
    private var button4: Button? = null
    private var button5: Button? = null
    private var button6: Button? = null
    private var button7: Button? = null
    private var button8: Button? = null
    private var pool: SimpleThreadPool? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pool = SimpleThreadPool(3)
        progressBar = findViewById<MyProgressBar>(R.id.progressbar)
        progressBar2 = findViewById<MyProgressBar>(R.id.progressbar2)
        progressBar3 = findViewById<MyProgressBar>(R.id.progressbar3)
        progressBar4 = findViewById<MyProgressBar>(R.id.progressbar4)
        progressBar5 = findViewById<MyProgressBar>(R.id.progressbar5)
        button1 = findViewById(R.id.button)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        button4 = findViewById(R.id.button4)
        button5 = findViewById(R.id.button5)
        button6 = findViewById(R.id.button6)
        button7 = findViewById(R.id.button7)
        button8 = findViewById(R.id.button8)
        button1?.setOnClickListener(this)
        button2?.setOnClickListener(this)
        button3?.setOnClickListener(this)
        button4?.setOnClickListener(this)
        button5?.setOnClickListener(this)
        button6?.setOnClickListener(this)
        button7?.setOnClickListener(this)
        button8?.setOnClickListener(this)

    }

    inner class Task() {
        private fun increase(progressBar: MyProgressBar): Runnable {
            return Runnable {

                for (i in 0..800) {
                    Thread.sleep((0..10).shuffled().last().toLong())
                    runOnUiThread{progressBar?.progress = i.toFloat()}

                }
            }
        }

        fun start(progressBar: MyProgressBar) {
                pool?.execute(increase(progressBar))
        }


    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.button -> {
                button2?.isEnabled = false
                button1?.isEnabled = false
                Task().start(progressBar!!)
            }
            R.id.button2 -> {

                button2?.isEnabled = false
                button3?.isEnabled = false
                button4?.isEnabled = false
                button1?.isEnabled = false

                Task().start(progressBar5!!)
                Task().start(progressBar4!!)
                Task().start(progressBar!!)
            }
            R.id.button3 ->{
                button2?.isEnabled = false
                button3?.isEnabled = false
                Task().start(progressBar5!!)
            }
            R.id.button4 -> {
                button2?.isEnabled = false
                button4?.isEnabled = false
                Task().start(progressBar4!!)
            }
            R.id.button5 -> {
                button7?.isEnabled = false
                button5?.isEnabled = false
                Task().start(progressBar3!!)
            }
            R.id.button6 -> {
                button7?.isEnabled = false
                button6?.isEnabled = false
                Task().start(progressBar2!!)
            }
            R.id.button7 -> {
                button7?.isEnabled = false
                button6?.isEnabled = false
                button5?.isEnabled = false
                Task().start(progressBar2!!)
                Task().start(progressBar3!!)
            }
            R.id.button8 -> {
                if (progressBar?.progress!! > 799
                    && progressBar2?.progress!! > 799
                    && progressBar3?.progress!! > 799
                    && progressBar4?.progress!! > 799
                    && progressBar5?.progress!! > 799
                ) {
                    button1?.isEnabled = true
                    button2?.isEnabled = true
                    button3?.isEnabled = true
                    button4?.isEnabled = true
                    button5?.isEnabled = true
                    button6?.isEnabled = true
                    button7?.isEnabled = true
                    progressBar?.progress = 0f
                    progressBar2?.progress = 0f
                    progressBar3?.progress = 0f
                    progressBar4?.progress = 0f
                    progressBar5?.progress = 0f

                }else{
                    Toast.makeText(this,"等待下载完成",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



}