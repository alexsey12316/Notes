package com.example.notes

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.database.DBHandler
import java.util.*

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val tempProgress:ProgressBar=findViewById(R.id.splashProgress)
        val tempTextView:TextView=findViewById(R.id.progressText)
        tempProgress.visibility=ProgressBar.VISIBLE
        tempTextView.text= getString(R.string.noticeLoad)
        val background= object: Thread() {
            override fun run() {
                try {
                    val dBHandler=DBHandler(baseContext)
                    sleep(1000)
                    tempTextView.text=getString(R.string.calendarLoad)
                    sleep(1000)
                    val splashIntent= Intent(baseContext,MainActivity::class.java)
                    startActivity(splashIntent)
                    finish()
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }
        }
        background.start()

    }
    override fun onRestart() {
        super.onRestart()
        val splashIntent= Intent(baseContext,MainActivity::class.java)
        startActivity(splashIntent)
        finish()
    }
}